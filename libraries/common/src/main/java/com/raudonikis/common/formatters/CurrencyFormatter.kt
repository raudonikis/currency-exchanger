package com.raudonikis.common.formatters

import com.raudonikis.common.extensions.round

object CurrencyFormatter {

    fun format(from: Double): String = from.round(2).toString()
}
