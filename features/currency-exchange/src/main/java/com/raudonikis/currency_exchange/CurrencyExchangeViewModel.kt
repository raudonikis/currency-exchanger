package com.raudonikis.currency_exchange

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.raudonikis.currency_exchange.model.Currency
import com.raudonikis.currency_exchange.model.CurrencyRate
import com.raudonikis.currency_exchange.repo.CurrencyBalancesRepository
import com.raudonikis.currency_exchange.repo.CurrencyRatesRepository
import com.raudonikis.currency_exchange.usecase.ConvertCurrencyResult
import com.raudonikis.currency_exchange.usecase.ConvertCurrencyUseCase
import com.raudonikis.currency_exchange.usecase.CurrencyCommissionUseCase
import com.raudonikis.data.models.CurrencyType
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import timber.log.Timber
import javax.inject.Inject

@HiltViewModel
class CurrencyExchangeViewModel @Inject constructor(
    balancesRepository: CurrencyBalancesRepository,
    ratesRepository: CurrencyRatesRepository,
    currencyCommissionUseCase: CurrencyCommissionUseCase,
    private val convertCurrencyUseCase: ConvertCurrencyUseCase,
) : ViewModel() {

    private val sellCurrency = MutableStateFlow<CurrencyType?>(null)
    private val sellValue = MutableStateFlow<Double?>(null)
    private val receiveCurrency = MutableStateFlow<CurrencyType?>(null)
    private val rate: StateFlow<CurrencyRate?> = combine(
        sellCurrency,
        receiveCurrency,
    ) { sellCurrency, receiveCurrency ->
        ratesRepository.getRate(sellCurrency, receiveCurrency)
    }
        .flatMapLatest { it }
        .flowOn(Dispatchers.IO)
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(), initialValue = null)

    /**
     * Observables
     */
    val balances = balancesRepository.balances
    val receiveValue: StateFlow<Double> = combine(rate, sellValue) { rate, sellValue ->
        if (rate == null || sellValue == null) {
            return@combine 0.0
        }
        rate.rate * sellValue
    }
        .flowOn(Dispatchers.IO)
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(), initialValue = 0.0)
    val commissionFee: StateFlow<Double> =
        combine(sellCurrency, sellValue) { sellCurrency, sellValue ->
            if (sellCurrency == null || sellValue == null) {
                return@combine flowOf(0.0)
            }
            currencyCommissionUseCase.getCommissionFee(Currency(sellCurrency, sellValue))
        }
            .flatMapLatest { it }
            .flowOn(Dispatchers.IO)
            .stateIn(viewModelScope, SharingStarted.WhileSubscribed(), initialValue = 0.0)

    private val _event = MutableSharedFlow<ConvertCurrencyResult>()
    val event = _event.asSharedFlow()

    /**
     * Events
     */

    fun onSellCurrencyTypeChanged(currency: CurrencyType) {
        sellCurrency.value = currency
    }

    fun onReceiveCurrencyTypeChanged(currency: CurrencyType) {
        receiveCurrency.value = currency
    }

    fun onSellValueChanged(sellValue: Double?) {
        this.sellValue.value = sellValue
    }

    fun onSubmitTransaction() {
        viewModelScope.launch {
            // todo cleanup
            val result = convertCurrencyUseCase.convert(
                from = Currency(
                    sellCurrency.value ?: return@launch,
                    sellValue.value ?: return@launch
                ),
                to = Currency(receiveCurrency.value ?: return@launch, receiveValue.value),
                rate = rate.value ?: return@launch,
                commissionFee = commissionFee.value
            )
            Timber.d("onSubmitTransaction result -> $result")
            _event.emit(result)
        }
    }
}
