package com.raudonikis.data.daos

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.raudonikis.data.entities.CurrencyTransactionEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface CurrencyTransactionsDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertOrUpdate(currencyTransaction: CurrencyTransactionEntity)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertOrUpdate(currencyTransactions: List<CurrencyTransactionEntity>)

    @Query("SELECT * FROM currency_transactions")
    fun getAll(): Flow<List<CurrencyTransactionEntity>>
}
