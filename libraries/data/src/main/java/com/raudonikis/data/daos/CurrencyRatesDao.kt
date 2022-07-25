package com.raudonikis.data.daos

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.raudonikis.data.entities.CurrencyRateEntity
import com.raudonikis.data.models.CurrencyType
import kotlinx.coroutines.flow.Flow

@Dao
interface CurrencyRatesDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertOrUpdate(currencyRate: CurrencyRateEntity)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertOrUpdate(currencyRates: List<CurrencyRateEntity>)

    @Query("SELECT * FROM currency_rates")
    fun getAll(): Flow<List<CurrencyRateEntity>>

    @Query("SELECT * FROM currency_rates WHERE currency_type=:currencyType LIMIT 1")
    fun getRate(currencyType: CurrencyType): Flow<CurrencyRateEntity?>
}
