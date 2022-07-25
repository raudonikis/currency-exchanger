package com.raudonikis.currency_exchange.repo

import com.raudonikis.currency_exchange.mapper.CurrencyEntityMapper
import com.raudonikis.currency_exchange.mapper.CurrencyMapper
import com.raudonikis.currency_exchange.model.Currency
import com.raudonikis.data.daos.CurrencyBalancesDao
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class CurrencyBalancesRepository @Inject constructor(
    private val currencyBalancesDao: CurrencyBalancesDao
) {
    val balances: Flow<List<Currency>> =
        currencyBalancesDao.getAll().map { CurrencyMapper.map(it) }

    suspend fun update(balance: Currency) {
        currencyBalancesDao.insertOrUpdate(CurrencyEntityMapper.map(balance))
    }
}
