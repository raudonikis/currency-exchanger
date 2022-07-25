package com.raudonikis.currency_exchange.usecase

import com.raudonikis.currency_exchange.model.Currency
import com.raudonikis.currency_exchange.repo.CurrencyTransactionsRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class CurrencyCommissionUseCase @Inject constructor(
    private val transactionsRepository: CurrencyTransactionsRepository
) {

    fun getCommissionFee(from: Currency): Flow<Double> {
        return transactionsRepository.transactions.map { transactions ->
            when {
                transactions.isEmpty() -> from.amount * 0.1
                transactions.size > 5 -> from.amount * 0.07
                else -> 0.0
            }
        }
    }
}
