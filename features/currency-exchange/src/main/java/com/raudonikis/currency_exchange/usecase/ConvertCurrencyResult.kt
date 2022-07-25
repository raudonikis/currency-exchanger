package com.raudonikis.currency_exchange.usecase

import com.raudonikis.currency_exchange.model.Currency

sealed class ConvertCurrencyResult {
    object FromBalanceNegative : ConvertCurrencyResult()
    data class Success(val from: Currency, val to: Currency, val fee: Double) :
        ConvertCurrencyResult()

    object Error : ConvertCurrencyResult()
}
