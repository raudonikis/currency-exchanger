package com.raudonikis.currency_exchange.repo

import com.raudonikis.currency_exchange.mapper.CurrencyEntityMapper
import com.raudonikis.currency_exchange.mapper.CurrencyMapper
import com.raudonikis.currency_exchange.model.Currency
import com.raudonikis.data.daos.CurrencyBalancesDao
import com.raudonikis.data.models.CurrencyType
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.withContext
import javax.inject.Inject

class CurrencyBalancesRepository @Inject constructor(
    private val currencyBalancesDao: CurrencyBalancesDao
) {
    val balances: Flow<List<Currency>> = currencyBalancesDao.getAll()
        .map { CurrencyMapper.map(it).sortedBy { it.currencyType.name } }

    suspend fun update(balance: Currency) {
        currencyBalancesDao.insertOrUpdate(CurrencyEntityMapper.map(balance))
    }

    suspend fun getBalance(currency: CurrencyType): Currency? = withContext(Dispatchers.IO) {
        CurrencyMapper.map(currencyBalancesDao.get(currency))
    }
}
