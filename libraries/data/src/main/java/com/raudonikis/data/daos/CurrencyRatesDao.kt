package com.raudonikis.data.daos

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.raudonikis.common.model.CurrencyType
import com.raudonikis.data.entities.CurrencyRateEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface CurrencyRatesDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertOrUpdate(currencyRate: CurrencyRateEntity)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertOrUpdate(currencyRates: List<CurrencyRateEntity>)

    @Query("DELETE FROM currency_rates")
    suspend fun deleteAll()

    @Query("SELECT * FROM currency_rates")
    fun getAll(): Flow<List<CurrencyRateEntity>>

    @Query("SELECT * FROM currency_rates WHERE currency_type=:currencyType LIMIT 1")
    fun getRateFlow(currencyType: CurrencyType): Flow<CurrencyRateEntity?>

    @Query("SELECT * FROM currency_rates WHERE currency_type=:currencyType LIMIT 1")
    suspend fun getRate(currencyType: CurrencyType): CurrencyRateEntity?
}
