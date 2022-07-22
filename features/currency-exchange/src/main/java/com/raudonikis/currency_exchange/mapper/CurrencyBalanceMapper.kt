package com.raudonikis.currency_exchange.mapper

import com.raudonikis.currency_exchange.model.CurrencyBalance
import com.raudonikis.data.entities.CurrencyBalanceEntity

object CurrencyBalanceMapper {

    private fun map(from: CurrencyBalanceEntity): CurrencyBalance {
        return CurrencyBalance(currencyType = from.currencyType, balance = from.balance)
    }

    fun map(from: List<CurrencyBalanceEntity>): List<CurrencyBalance> {
        return from.map { map(it) }
    }
}
