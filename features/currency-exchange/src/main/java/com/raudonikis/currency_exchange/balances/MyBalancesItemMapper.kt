package com.raudonikis.currency_exchange.balances

import com.raudonikis.currency_exchange.model.Currency

object MyBalancesItemMapper {

    fun map(from: List<Currency>): List<MyBalancesItem> {
        return from.map { MyBalancesItem(it) }
    }
}
