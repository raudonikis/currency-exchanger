package com.raudonikis.currency_exchange.repo

import com.raudonikis.currency_exchange.mapper.CurrencyRateEntityMapper
import com.raudonikis.currency_exchange.mapper.CurrencyRateMapper
import com.raudonikis.currency_exchange.model.CurrencyRate
import com.raudonikis.data.daos.CurrencyRatesDao
import com.raudonikis.data.models.CurrencyType
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOf
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

    fun getRate(from: CurrencyType?, to: CurrencyType?): Flow<CurrencyRate?> {
        return when {
            from == null || to == null -> flowOf()
            // FROM BASE -> simply fetch the [to] rate
            from == CurrencyType.BASE_TYPE -> currencyRatesDao.getRate(to)
            // TO BASE -> fetch the [from] rate and use reverse value
            to == CurrencyType.BASE_TYPE -> currencyRatesDao.getRate(from)
                .map { currencyRateEntity -> currencyRateEntity?.copy(rate = 1 / currencyRateEntity.rate) }
            // None of the values are BASE -> todo
            else -> flowOf()
        }.map { CurrencyRateMapper.map(it) }
    }
}
