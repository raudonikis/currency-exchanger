package com.raudonikis.data.entities

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.raudonikis.data.models.CurrencyType

@Entity(tableName = "currency_balances")
data class CurrencyBalanceEntity(
    @PrimaryKey
    @ColumnInfo(name = "currency_type")
    val currencyType: CurrencyType,
    @ColumnInfo(name = "balance")
    val balance: Double,
)
