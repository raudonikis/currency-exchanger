package com.raudonikis.currency_exchange.repo

import com.raudonikis.currency_exchange.mapper.CurrencyRateEntityMapper
import com.raudonikis.currency_exchange.mapper.CurrencyRateMapper
import com.raudonikis.currency_exchange.model.CurrencyRate
import com.raudonikis.data.daos.CurrencyRatesDao
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class CurrencyRatesRepository @Inject constructor(
    private val currencyRatesDao: CurrencyRatesDao
) {
    val rates: Flow<List<CurrencyRate>> =
        currencyRatesDao.getAll().map { CurrencyRateMapper.map(it) }

    suspend fun update(rates: List<CurrencyRate>) {
        currencyRatesDao.insertOrUpdate(CurrencyRateEntityMapper.map(rates))
    }
}
