package com.raudonikis.currency_exchange.mapper

import com.raudonikis.currency_exchange.model.CurrencyRate
import com.raudonikis.data.entities.CurrencyRateEntity

object CurrencyRateMapper {

    private fun map(from: CurrencyRateEntity): CurrencyRate {
        return CurrencyRate(currencyType = from.currencyType, rate = from.rate)
    }

    fun map(from: List<CurrencyRateEntity>): List<CurrencyRate> {
        return from.map { map(it) }
    }
}
