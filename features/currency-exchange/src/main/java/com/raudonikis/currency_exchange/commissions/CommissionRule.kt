package com.raudonikis.currency_exchange.commissions

import android.text.format.DateUtils
import com.raudonikis.currency_exchange.model.Currency
import com.raudonikis.currency_exchange.model.CurrencyTransaction

sealed class CommissionRule {

    /**
     * Enables after the first [maxPerDay] transactions in a single day
     */
    data class MaxPerDay(
        val maxPerDay: Int,
        val interest: Double,
        val eurEquivalent: Double
    ) : CommissionRule() {

        override fun calculate(
            from: Currency,
            currencyRate: Currency?,
            transactions: List<CurrencyTransaction>
        ): CommissionRuleResult {
            val today = transactions.count { DateUtils.isToday(it.timestamp) }
            if (today < maxPerDay) {
                return CommissionRuleResult.NotSuitable
            }
            val rate = currencyRate?.amount ?: 0.0
            return CommissionRuleResult.Suitable(
                fee = interest * from.amount + (eurEquivalent * rate)
            )
        }
    }

    /**
     * Enables after the first [after] transactions total
     */
    data class AfterFirst(val after: Int, val interest: Double) : CommissionRule() {

        override fun calculate(
            from: Currency,
            currencyRate: Currency?,
            transactions: List<CurrencyTransaction>
        ): CommissionRuleResult {
            if (transactions.size < after) {
                return CommissionRuleResult.NotSuitable
            }
            return CommissionRuleResult.Suitable(fee = interest * from.amount)
        }
    }

    abstract fun calculate(
        from: Currency,
        currencyRate: Currency?,
        transactions: List<CurrencyTransaction>
    ): CommissionRuleResult
}
