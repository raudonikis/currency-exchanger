package com.raudonikis.currency_exchange.mapper

import com.raudonikis.currency_exchange.model.CurrencyRate
import com.raudonikis.data.entities.CurrencyRateEntity

object CurrencyRateMapper {

    fun map(from: CurrencyRateEntity?): CurrencyRate? {
        from ?: return null
        return CurrencyRate(currencyType = from.currencyType, rate = from.rate)
    }

    fun map(from: List<CurrencyRateEntity>): List<CurrencyRate> {
        return from.mapNotNull { map(it) }
    }
}
