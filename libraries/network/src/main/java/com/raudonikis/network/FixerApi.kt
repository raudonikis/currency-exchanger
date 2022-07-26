package com.raudonikis.network

import com.raudonikis.common.model.CurrencyType
import com.raudonikis.network.responses.LatestCurrencyRatesResponse
import retrofit2.http.GET
import retrofit2.http.Query

interface FixerApi {

    @GET("latest")
    suspend fun latestRates(
        @Query("symbols") currencyTypes: String = CurrencyType.values().joinToString(",")
    ): LatestCurrencyRatesResponse
}
