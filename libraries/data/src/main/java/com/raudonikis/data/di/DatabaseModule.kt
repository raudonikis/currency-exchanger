package com.raudonikis.data.di

import android.content.Context
import androidx.room.Room
import com.raudonikis.data.CurrencyExchangerDatabase
import com.raudonikis.data.daos.CurrencyBalancesDao
import com.raudonikis.data.daos.CurrencyRatesDao
import com.raudonikis.data.daos.CurrencyTransactionsDao
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DatabaseModule {

    @Provides
    @Singleton
    fun provideExampleDatabase(@ApplicationContext appContext: Context): CurrencyExchangerDatabase {
        return Room.databaseBuilder(
            appContext,
            CurrencyExchangerDatabase::class.java,
            CurrencyExchangerDatabase.DATABASE_NAME
        ).build()
    }

    @Provides
    @Singleton
    fun provideBalancesDao(database: CurrencyExchangerDatabase): CurrencyBalancesDao {
        return database.balancesDao()
    }

    @Provides
    @Singleton
    fun provideCurrencyRatesDao(database: CurrencyExchangerDatabase): CurrencyRatesDao {
        return database.currencyRatesDao()
    }

    @Provides
    @Singleton
    fun provideCurrencyTransactionsDao(database: CurrencyExchangerDatabase): CurrencyTransactionsDao {
        return database.currencyTransactionsDao()
    }
}
