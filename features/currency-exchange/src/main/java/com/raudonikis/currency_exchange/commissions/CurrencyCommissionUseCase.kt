package com.raudonikis.currency_exchange.commissions

import com.raudonikis.currency_exchange.model.Currency
import com.raudonikis.currency_exchange.repo.CurrencyRatesRepository
import com.raudonikis.currency_exchange.repo.CurrencyTransactionsRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import timber.log.Timber
import javax.inject.Inject

class CurrencyCommissionUseCase @Inject constructor(
    private val transactionsRepository: CurrencyTransactionsRepository,
    private val ratesRepository: CurrencyRatesRepository
) {

    // Will take the first suitable rule from the list
    private val commissionRules = listOf(
        CommissionRule.MaxPerDay(maxPerDay = 15, interest = 0.12, eurEquivalent = 0.3),
        CommissionRule.AfterFirst(after = 5, interest = 0.07),
    )

    suspend fun getCommissionFee(from: Currency): Flow<Double> {
        val baseRate = ratesRepository.getBaseRate(from.currencyType)
        return transactionsRepository.transactions.map { transactions ->
            val rule = commissionRules
                .map { rule -> rule.calculate(from, baseRate, transactions) }
                .filterIsInstance(CommissionRuleResult.Suitable::class.java)
                .firstOrNull()
            Timber.d("getCommissionFee -> $rule")
            rule?.fee ?: 0.0
        }
    }
}
