package com.raudonikis.data.daos

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.raudonikis.common.model.CurrencyType
import com.raudonikis.data.entities.CurrencyEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface CurrencyBalancesDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertOrUpdate(balance: CurrencyEntity)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertOrUpdate(balances: List<CurrencyEntity>)

    @Query("SELECT * FROM currencies")
    fun getAll(): Flow<List<CurrencyEntity>>

    @Query("SELECT * FROM currencies WHERE currency_type=:currencyType LIMIT 1")
    fun get(currencyType: CurrencyType): CurrencyEntity?
}
