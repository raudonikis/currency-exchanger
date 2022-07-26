package com.raudonikis.currency_exchange.mapper

import com.raudonikis.currency_exchange.model.Currency
import com.raudonikis.data.entities.CurrencyEntity
import com.raudonikis.data.entities.CurrencyRateEntity

object CurrencyMapper {

    fun map(from: CurrencyEntity?): Currency? {
        from ?: return null
        return Currency(currencyType = from.currencyType, amount = from.amount)
    }

    fun mapCurrencyEntities(from: List<CurrencyEntity>): List<Currency> {
        return from.mapNotNull { map(it) }
    }

    fun map(from: CurrencyRateEntity?): Currency? {
        from ?: return null
        return Currency(currencyType = from.currencyType, amount = from.rate)
    }
}
