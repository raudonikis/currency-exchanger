package com.raudonikis.currency_exchange.repo

import com.raudonikis.currency_exchange.mapper.CurrencyBalanceEntityMapper
import com.raudonikis.currency_exchange.mapper.CurrencyBalanceMapper
import com.raudonikis.currency_exchange.model.CurrencyBalance
import com.raudonikis.data.daos.CurrencyBalancesDao
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class CurrencyBalancesRepository @Inject constructor(
    private val currencyBalancesDao: CurrencyBalancesDao
) {
    val balances: Flow<List<CurrencyBalance>> =
        currencyBalancesDao.getAll().map { CurrencyBalanceMapper.map(it) }

    suspend fun update(balance: CurrencyBalance) {
        currencyBalancesDao.insertOrUpdate(CurrencyBalanceEntityMapper.map(balance))
    }
}
