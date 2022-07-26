package com.raudonikis.currency_exchange

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.raudonikis.common.coroutines.CoroutinesDispatcherProvider
import com.raudonikis.currency_exchange.balances.CurrencyBalancesRepository
import com.raudonikis.currency_exchange.commissions.CurrencyCommissionUseCase
import com.raudonikis.currency_exchange.convert.ConvertCurrencyResult
import com.raudonikis.currency_exchange.convert.ConvertCurrencyUseCase
import com.raudonikis.currency_exchange.model.Currency
import com.raudonikis.currency_exchange.repo.CurrencyRatesRepository
import com.raudonikis.common.model.CurrencyType
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import timber.log.Timber
import javax.inject.Inject

@HiltViewModel
class CurrencyExchangeViewModel @Inject constructor(
    balancesRepository: CurrencyBalancesRepository,
    dispatchers: CoroutinesDispatcherProvider,
    private val ratesRepository: CurrencyRatesRepository,
    private val currencyCommissionUseCase: CurrencyCommissionUseCase,
    private val convertCurrencyUseCase: ConvertCurrencyUseCase,
) : ViewModel() {

    private val sellCurrency = MutableStateFlow<CurrencyType?>(null)
    private val sellValue = MutableStateFlow(0.0)
    private val receiveCurrency = MutableStateFlow<CurrencyType?>(null)
    private val conversionRate: StateFlow<Currency?> = combine(
        sellCurrency,
        receiveCurrency,
    ) { sellCurrency, receiveCurrency -> ratesRepository.getRate(sellCurrency, receiveCurrency) }
        .flatMapLatest { it }
        .flowOn(dispatchers.io)
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(), initialValue = null)

    /**
     * Observables
     */
    val balances = balancesRepository.balances

    val receiveValue: StateFlow<Double> = combine(
        conversionRate,
        sellValue
    ) { conversionRate, sellValue -> combineReceiveValue(conversionRate, sellValue) }
        .flowOn(dispatchers.io)
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(), initialValue = 0.0)

    val commissionFee: StateFlow<Double> = combine(
        sellCurrency,
        sellValue
    ) { sellCurrency, sellValue -> combineCommissionFee(sellCurrency, sellValue) }
        .flatMapLatest { it }
        .flowOn(dispatchers.io)
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(), initialValue = 0.0)

    private val _conversionResult = MutableSharedFlow<ConvertCurrencyResult>()
    val conversionResult = _conversionResult.asSharedFlow()

    val isValid = receiveValue.map { it > 0 }.flowOn(dispatchers.io)

    init {
        synchroniseLatestRates()
    }

    /**
     * Keep synchronising the latest conversion rates, every [DELAY_LATEST_RATES_SYNC] ms
     */
    private fun synchroniseLatestRates() {
        viewModelScope.launch {
            while (true) {
                ratesRepository.updateRates()
                delay(DELAY_LATEST_RATES_SYNC)
            }
        }
    }

    /**
     * Combiners
     */

    private fun combineReceiveValue(conversionRate: Currency?, sellValue: Double): Double {
        if (conversionRate == null) {
            Timber.e("conversionRate -> null")
            return 0.0
        }
        return conversionRate.amount * sellValue
    }

    private suspend fun combineCommissionFee(
        sellCurrency: CurrencyType?,
        sellValue: Double
    ): Flow<Double> {
        if (sellCurrency == null) {
            Timber.e("sellCurrency -> null")
            return flowOf(0.0)
        }
        return currencyCommissionUseCase.getCommissionFee(Currency(sellCurrency, sellValue))
    }

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
        this.sellValue.value = sellValue ?: 0.0
    }

    fun onSubmitTransaction() {
        val sellCurrencyType = sellCurrency.value
        val receiveCurrencyType = receiveCurrency.value
        val conversionRate = conversionRate.value
        if (sellCurrencyType == null || receiveCurrencyType == null || conversionRate == null) {
            Timber.e("onSubmitTransaction -> null values -> sellCurrencyType: $sellCurrencyType, receiveCurrencyType: $receiveCurrencyType, conversionRate: $conversionRate")
            return
        }
        viewModelScope.launch {
            val result = convertCurrencyUseCase.convert(
                from = Currency(sellCurrencyType, sellValue.value),
                to = Currency(receiveCurrencyType, receiveValue.value),
                rate = conversionRate,
                commissionFee = commissionFee.value
            )
            Timber.d("onSubmitTransaction result -> $result")
            _conversionResult.emit(result)
        }
    }

    companion object {
        private const val DELAY_LATEST_RATES_SYNC = 15_000L // 15 seconds
    }
}
