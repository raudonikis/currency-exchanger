package com.raudonikis.currency_exchange.repo

import com.raudonikis.common.coroutines.CoroutinesDispatcherProvider
import com.raudonikis.currency_exchange.mapper.CurrencyMapper
import com.raudonikis.currency_exchange.mapper.CurrencyRateEntityMapper
import com.raudonikis.currency_exchange.model.Currency
import com.raudonikis.data.daos.CurrencyRatesDao
import com.raudonikis.data.entities.CurrencyRateEntity
import com.raudonikis.data.models.CurrencyType
import com.raudonikis.network.FixerApi
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.withContext
import timber.log.Timber
import javax.inject.Inject

class CurrencyRatesRepository @Inject constructor(
    private val currencyRatesDao: CurrencyRatesDao,
    private val fixerApi: FixerApi,
    private val dispatchers: CoroutinesDispatcherProvider,
) {

    suspend fun getBaseRate(from: CurrencyType): Currency? = withContext(dispatchers.io) {
        CurrencyMapper.map(currencyRatesDao.getRate(from))
    }

    fun getRate(from: CurrencyType?, to: CurrencyType?): Flow<Currency?> {
        return when {
            from == null || to == null -> flowOf()
            // FROM BASE -> simply fetch the [to] rate
            from == CurrencyType.BASE_TYPE -> currencyRatesDao.getRateFlow(to)
            // TO BASE -> fetch the [from] rate and use reverse value
            to == CurrencyType.BASE_TYPE -> currencyRatesDao.getRateFlow(from)
                .map { currencyRateEntity -> currencyRateEntity?.copy(rate = 1 / currencyRateEntity.rate) }
            // None of the values are BASE -> divide from each other
            else -> combine(
                currencyRatesDao.getRateFlow(to),
                currencyRatesDao.getRateFlow(from)
            ) { rateTo, rateFrom ->
                if (rateTo == null || rateFrom == null) {
                    Timber.e("No rates have been found for [$to] and [$from]")
                    return@combine null
                }
                CurrencyRateEntity(rateTo.currencyType, rate = rateTo.rate / rateFrom.rate)
            }
        }.map { CurrencyMapper.map(it) }
    }

    suspend fun updateRates() {
        withContext(dispatchers.io) {
            val latestRates = fixerApi.latestRates()
            currencyRatesDao.insertOrUpdate(CurrencyRateEntityMapper.map(latestRates))
        }
    }
}
