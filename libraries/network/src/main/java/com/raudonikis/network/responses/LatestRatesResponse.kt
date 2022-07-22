package com.raudonikis.network.responses

import com.squareup.moshi.Json

data class LatestRatesResponse(
    @Json(name = "base")
    val base: String? = null,
    @Json(name = "date")
    val date: String? = null,
    @Json(name = "rates")
    val rates: RatesResponse? = RatesResponse(),
    @Json(name = "success")
    val success: Boolean? = null,
    @Json(name = "timestamp")
    val timestamp: Int? = null
)
