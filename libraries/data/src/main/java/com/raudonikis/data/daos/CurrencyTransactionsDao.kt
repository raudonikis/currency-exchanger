package com.raudonikis.data.daos

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import com.raudonikis.data.entities.CurrencyTransactionEntity

@Dao
interface CurrencyTransactionsDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(currencyTransaction: CurrencyTransactionEntity)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(currencyTransactions: List<CurrencyTransactionEntity>)
}
