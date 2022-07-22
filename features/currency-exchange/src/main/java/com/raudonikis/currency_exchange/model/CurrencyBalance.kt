package com.raudonikis.currency_exchange.model

import com.raudonikis.data.models.CurrencyType

data class CurrencyBalance(
    val currencyType: CurrencyType,
    val balance: Double
)
