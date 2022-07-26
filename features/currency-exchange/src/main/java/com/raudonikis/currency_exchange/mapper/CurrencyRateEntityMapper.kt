package com.raudonikis.currency_exchange.mapper

import com.raudonikis.currency_exchange.model.Currency
import com.raudonikis.data.entities.CurrencyRateEntity
import com.raudonikis.data.models.CurrencyType
import com.raudonikis.network.responses.LatestCurrencyRatesResponse

object CurrencyRateEntityMapper {

    fun map(from: Currency): CurrencyRateEntity {
        return CurrencyRateEntity(currencyType = from.currencyType, rate = from.amount)
    }

    fun map(from: List<Currency>): List<CurrencyRateEntity> {
        return from.map { map(it) }
    }

    fun map(from: LatestCurrencyRatesResponse): List<CurrencyRateEntity> {
        return from.rates.mapNotNull { entry ->
            val currencyType = try {
                CurrencyType.valueOf(entry.key)
            } catch (e: Exception) {
                null
            } ?: return@mapNotNull null
            CurrencyRateEntity(currencyType = currencyType, rate = entry.value)
        }
    }
}
