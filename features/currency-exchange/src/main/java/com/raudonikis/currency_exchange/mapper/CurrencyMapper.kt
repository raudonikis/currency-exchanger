package com.raudonikis.currency_exchange.mapper

import com.raudonikis.currency_exchange.model.Currency
import com.raudonikis.data.entities.CurrencyEntity

object CurrencyMapper {

    fun map(from: CurrencyEntity?): Currency? {
        from ?: return null
        return Currency(currencyType = from.currencyType, amount = from.amount)
    }

    fun map(from: List<CurrencyEntity>): List<Currency> {
        return from.mapNotNull { map(it) }
    }
}
