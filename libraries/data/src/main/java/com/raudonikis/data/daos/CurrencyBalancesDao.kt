package com.raudonikis.data.daos

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.raudonikis.data.entities.CurrencyBalanceEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface CurrencyBalancesDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertOrUpdate(balance: CurrencyBalanceEntity)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertOrUpdate(balances: List<CurrencyBalanceEntity>)

    @Query("SELECT * FROM currency_balances")
    fun getAll(): Flow<List<CurrencyBalanceEntity>>
}
