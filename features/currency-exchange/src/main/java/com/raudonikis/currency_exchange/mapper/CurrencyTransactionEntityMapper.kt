package com.raudonikis.currency_exchange.mapper

import com.raudonikis.currency_exchange.model.CurrencyTransaction
import com.raudonikis.data.entities.CurrencyTransactionEntity

object CurrencyTransactionEntityMapper {

    fun map(from: CurrencyTransaction): CurrencyTransactionEntity {
        return CurrencyTransactionEntity(id = 0, timestamp = from.timestamp)
    }

    fun map(from: List<CurrencyTransaction>): List<CurrencyTransactionEntity> {
        return from.map { map(it) }
    }
}
