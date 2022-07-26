package com.raudonikis.network.di

import com.raudonikis.network.FIXER_API_BASE_URL
import com.raudonikis.network.FixerApi
import com.raudonikis.network.FixerApiInterceptor
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object NetworkModule {

    @Provides
    @Singleton
    fun provideFixerApiService(
        okHttpClient: OkHttpClient,
        moshiConverterFactory: MoshiConverterFactory
    ): FixerApi {
        return Retrofit.Builder()
            .baseUrl(FIXER_API_BASE_URL)
            .client(okHttpClient)
            .addConverterFactory(moshiConverterFactory)
            .build()
            .create(FixerApi::class.java)
    }

    @Provides
    internal fun provideOkHttpClient(
        apiInterceptor: FixerApiInterceptor,
        httpLoggingInterceptor: HttpLoggingInterceptor
    ): OkHttpClient {
        return OkHttpClient.Builder()
            .addInterceptor(apiInterceptor)
            .addInterceptor(httpLoggingInterceptor)
            .build()
    }

    @Provides
    internal fun provideHttpLoggingInterceptor(): HttpLoggingInterceptor {
        return HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY)
    }
}
