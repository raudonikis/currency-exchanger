package com.raudonikis.common.model

/**
 * Currency types used in the app
 * Simply add a new currency type here and it will appear in the app
 */
enum class CurrencyType {
    EUR,
    JPY,
    LTL,
    LVL,
    GBP;

    companion object {
        val BASE_TYPE = EUR
    }
}
