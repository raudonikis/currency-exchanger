package com.raudonikis.network

import com.raudonikis.network.responses.LatestRatesResponse
import retrofit2.http.GET

interface FixerApi {

    @GET("latest")
    suspend fun latestRates(): LatestRatesResponse
}
