package com.raudonikis.network.responses

import com.squareup.moshi.Json

data class LatestCurrencyRatesResponse(
    @Json(name = "base")
    val base: String,
    @Json(name = "date")
    val date: String,
    @Json(name = "rates")
    val rates: Map<String, Double>,
    @Json(name = "success")
    val success: Boolean,
    @Json(name = "timestamp")
    val timestamp: Int
)
