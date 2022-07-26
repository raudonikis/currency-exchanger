package com.raudonikis.currency_exchange.mapper

import com.raudonikis.currency_exchange.model.Currency
import com.raudonikis.data.entities.CurrencyEntity

object CurrencyEntityMapper {

    fun map(from: Currency): CurrencyEntity {
        return CurrencyEntity(currencyType = from.currencyType, amount = from.amount)
    }

    fun map(from: List<Currency>): List<CurrencyEntity> {
        return from.map { map(it) }
    }
}
