package com.raudonikis.currency_exchange.mapper

import com.raudonikis.currency_exchange.model.CurrencyBalance
import com.raudonikis.data.entities.CurrencyBalanceEntity

object CurrencyBalanceEntityMapper {

    fun map(from: CurrencyBalance): CurrencyBalanceEntity {
        return CurrencyBalanceEntity(currencyType = from.currencyType, balance = from.balance)
    }

    fun map(from: List<CurrencyBalance>): List<CurrencyBalanceEntity> {
        return from.map { map(it) }
    }
}
