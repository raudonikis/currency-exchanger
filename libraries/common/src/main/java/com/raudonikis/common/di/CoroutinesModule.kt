package com.raudonikis.common.di

import com.raudonikis.common.coroutines.CoroutinesDispatcherProvider
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import kotlinx.coroutines.Dispatchers

@InstallIn(SingletonComponent::class)
@Module
object CoroutinesModule {

    @Provides
    fun provideCoroutinesDispatcherProvider(): CoroutinesDispatcherProvider =
        CoroutinesDispatcherProvider(
            io = Dispatchers.IO,
            main = Dispatchers.Main,
            default = Dispatchers.Default
        )
}
