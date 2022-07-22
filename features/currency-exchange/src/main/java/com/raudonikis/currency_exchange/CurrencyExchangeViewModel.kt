package com.raudonikis.currency_exchange

import androidx.lifecycle.ViewModel
import com.raudonikis.currency_exchange.repo.CurrencyBalancesRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class CurrencyExchangeViewModel @Inject constructor(
    private val balancesRepository: CurrencyBalancesRepository
) : ViewModel() {
    val balances = balancesRepository.balances
}
