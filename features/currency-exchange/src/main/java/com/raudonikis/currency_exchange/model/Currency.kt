package com.raudonikis.currency_exchange.model

import com.raudonikis.data.models.CurrencyType

data class Currency(
    val currencyType: CurrencyType,
    val amount: Double
)
