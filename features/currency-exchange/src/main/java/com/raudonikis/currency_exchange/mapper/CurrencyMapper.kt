package com.raudonikis.currency_exchange.mapper

import com.raudonikis.currency_exchange.model.Currency
import com.raudonikis.data.entities.CurrencyEntity

object CurrencyMapper {

    private fun map(from: CurrencyEntity): Currency {
        return Currency(currencyType = from.currencyType, amount = from.amount)
    }

    fun map(from: List<CurrencyEntity>): List<Currency> {
        return from.map { map(it) }
    }
}
