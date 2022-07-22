package com.raudonikis.currency_exchange.mapper

import com.raudonikis.currency_exchange.model.CurrencyRate
import com.raudonikis.data.entities.CurrencyRateEntity

object CurrencyRateEntityMapper {

    fun map(from: CurrencyRate): CurrencyRateEntity {
        return CurrencyRateEntity(currencyType = from.currencyType, rate = from.rate)
    }

    fun map(from: List<CurrencyRate>): List<CurrencyRateEntity> {
        return from.map { map(it) }
    }
}
