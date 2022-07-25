package com.raudonikis.currency_exchange.model

import com.raudonikis.common.formatters.CurrencyFormatter
import com.raudonikis.data.models.CurrencyType

data class Currency(
    val currencyType: CurrencyType,
    val amount: Double
) {
    override fun toString(): String {
        return "${CurrencyFormatter.format(amount)} ${currencyType.name}"
    }
}
