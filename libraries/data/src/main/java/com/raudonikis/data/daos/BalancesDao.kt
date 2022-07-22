package com.raudonikis.data.daos

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import com.raudonikis.data.entities.BalanceEntity

@Dao
interface BalancesDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(balance: BalanceEntity)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(balances: List<BalanceEntity>)
}
