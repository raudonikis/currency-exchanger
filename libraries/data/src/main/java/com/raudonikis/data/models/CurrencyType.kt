package com.raudonikis.data.models

/**
 * Currency types used in the app
 * Simply add a new currency type here and it will appear in the app
 */
enum class CurrencyType {
    EUR,
    JPY,
    KES,
    LTL,
    LVL,
    NPR,
    NZD,
    OMR,
    PAB,
    PEN,
    PGK,
    PHP,
    PKR,
    PLN,
    PYG,
    QAR,
    RON,
    RSD,
    RUB,
    RWF,
    USD,
    UYU,
    UZS,
    VND,
    VUV,
    WST,
    XAF,
    XAG,
    XAU,
    XCD,
    XDR,
    XOF,
    XPF,
    YER,
    ZAR,
    ZMK,
    ZMW,
    ZWL;

    companion object {
        val BASE_TYPE = EUR
    }
}
