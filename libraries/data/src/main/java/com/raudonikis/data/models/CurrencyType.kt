package com.raudonikis.data.models

enum class CurrencyType {
    EUR,
    LTL,
    USD,
    GBP,
    JPY;

    companion object {
        val BASE_TYPE = EUR
    }
}
