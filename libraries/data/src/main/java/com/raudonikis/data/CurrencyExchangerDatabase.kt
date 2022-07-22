package com.raudonikis.data

import androidx.room.Database
import androidx.room.RoomDatabase
import com.raudonikis.data.daos.BalancesDao
import com.raudonikis.data.daos.CurrencyRatesDao
import com.raudonikis.data.daos.CurrencyTransactionsDao
import com.raudonikis.data.entities.BalanceEntity
import com.raudonikis.data.entities.CurrencyRateEntity
import com.raudonikis.data.entities.CurrencyTransactionEntity

@Database(
    entities = [
        BalanceEntity::class,
        CurrencyRateEntity::class,
        CurrencyTransactionEntity::class,
    ],
    version = 1,
    exportSchema = false
)
abstract class CurrencyExchangerDatabase : RoomDatabase() {

    abstract fun balancesDao(): BalancesDao
    abstract fun currencyRatesDao(): CurrencyRatesDao
    abstract fun currencyTransactionsDao(): CurrencyTransactionsDao

    companion object {
        const val DATABASE_NAME = "currency_exchanger_db.db"
    }
}
