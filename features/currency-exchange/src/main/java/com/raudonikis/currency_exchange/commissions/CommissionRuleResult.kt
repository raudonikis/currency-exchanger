package com.raudonikis.currency_exchange.commissions

sealed class CommissionRuleResult {
    object NotSuitable : CommissionRuleResult()
    data class Suitable(val fee: Double) : CommissionRuleResult()
}
