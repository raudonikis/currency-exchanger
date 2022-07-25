package com.raudonikis.currency_exchange.repo

import com.raudonikis.currency_exchange.mapper.CurrencyTransactionEntityMapper
import com.raudonikis.currency_exchange.mapper.CurrencyTransactionMapper
import com.raudonikis.currency_exchange.model.CurrencyTransaction
import com.raudonikis.data.daos.CurrencyTransactionsDao
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class CurrencyTransactionsRepository @Inject constructor(
    private val currencyTransactionsDao: CurrencyTransactionsDao
) {

    val transactions: Flow<List<CurrencyTransaction>> =
        currencyTransactionsDao.getAll().map { CurrencyTransactionMapper.map(it) }

    suspend fun insert() {
        currencyTransactionsDao.insertOrUpdate(
            CurrencyTransactionEntityMapper.map(
                CurrencyTransaction()
            )
        )
    }
}
