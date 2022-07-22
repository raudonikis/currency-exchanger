package com.raudonikis.network.responses

import com.squareup.moshi.Json

data class RatesResponse(
    @Json(name = "EUR")
    val eur: Double? = null,
    @Json(name = "LTL")
    val ltl: Double? = null,
    @Json(name = "USD")
    val usd: Double? = null,
)
