package com.raudonikis.currency_exchange.balances

import com.raudonikis.common.coroutines.CoroutinesDispatcherProvider
import com.raudonikis.currency_exchange.mapper.CurrencyEntityMapper
import com.raudonikis.currency_exchange.mapper.CurrencyMapper
import com.raudonikis.currency_exchange.model.Currency
import com.raudonikis.data.daos.CurrencyBalancesDao
import com.raudonikis.data.models.CurrencyType
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.withContext
import javax.inject.Inject

class CurrencyBalancesRepository @Inject constructor(
    private val currencyBalancesDao: CurrencyBalancesDao,
    private val dispatchers: CoroutinesDispatcherProvider,
) {
    val balances: Flow<List<Currency>> = currencyBalancesDao.getAll()
        .map { entities ->
            CurrencyMapper.mapCurrencyEntities(entities).sortedBy { it.currencyType.name }
        }

    suspend fun update(balance: Currency) {
        currencyBalancesDao.insertOrUpdate(CurrencyEntityMapper.map(balance))
    }

    suspend fun getBalance(currency: CurrencyType): Currency? = withContext(dispatchers.io) {
        CurrencyMapper.map(currencyBalancesDao.get(currency))
    }

    suspend fun initializeBaseCurrency() {
        withContext(dispatchers.io) {
            update(Currency(CurrencyType.BASE_TYPE, BASE_CURRENCY_INITIAL))
        }
    }

    companion object {
        private const val BASE_CURRENCY_INITIAL = 1000.0
    }
}
