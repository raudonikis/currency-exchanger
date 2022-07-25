package com.raudonikis.currency_exchange

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.raudonikis.currency_exchange.model.CurrencyRate
import com.raudonikis.currency_exchange.repo.CurrencyBalancesRepository
import com.raudonikis.currency_exchange.repo.CurrencyRatesRepository
import com.raudonikis.data.models.CurrencyType
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.stateIn
import javax.inject.Inject

@HiltViewModel
class CurrencyExchangeViewModel @Inject constructor(
    balancesRepository: CurrencyBalancesRepository,
    ratesRepository: CurrencyRatesRepository,
) : ViewModel() {

    private val sellCurrency = MutableStateFlow<CurrencyType?>(null)
    private val receiveCurrency = MutableStateFlow<CurrencyType?>(null)

    /**
     * Observables
     */

    val balances = balancesRepository.balances
    val rate: StateFlow<CurrencyRate?> = combine(
        sellCurrency,
        receiveCurrency,
    ) { sellCurrency, receiveCurrency ->
        ratesRepository.getRate(sellCurrency, receiveCurrency)
    }
        .flatMapLatest { it }
        .flowOn(Dispatchers.IO)
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(), initialValue = null)

    /**
     * Events
     */

    fun onSellCurrencyTypeChanged(currency: CurrencyType) {
        sellCurrency.value = currency
    }

    fun onReceiveCurrencyTypeChanged(currency: CurrencyType) {
        receiveCurrency.value = currency
    }

    fun onSubmitTransaction() {
        // todo
    }
}
