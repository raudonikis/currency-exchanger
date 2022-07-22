package com.raudonikis.currency_exchange.model

import com.raudonikis.data.models.CurrencyType

data class CurrencyRate(
    val currencyType: CurrencyType,
    val rate: Double
)
