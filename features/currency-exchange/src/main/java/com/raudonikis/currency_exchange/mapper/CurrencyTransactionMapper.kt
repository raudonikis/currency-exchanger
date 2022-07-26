package com.raudonikis.currency_exchange.mapper

import com.raudonikis.currency_exchange.model.CurrencyTransaction
import com.raudonikis.data.entities.CurrencyTransactionEntity

object CurrencyTransactionMapper {

    private fun map(from: CurrencyTransactionEntity): CurrencyTransaction {
        return CurrencyTransaction(timestamp = from.timestamp)
    }

    fun map(from: List<CurrencyTransactionEntity>): List<CurrencyTransaction> {
        return from.map { map(it) }
    }
}
