package com.raudonikis.currency_exchange.usecase

import com.raudonikis.currency_exchange.model.Currency
import com.raudonikis.currency_exchange.model.CurrencyRate
import com.raudonikis.currency_exchange.repo.CurrencyBalancesRepository
import com.raudonikis.currency_exchange.repo.CurrencyTransactionsRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

class ConvertCurrencyUseCase @Inject constructor(
    private val transactionsRepository: CurrencyTransactionsRepository,
    private val balancesRepository: CurrencyBalancesRepository,
) {

    suspend fun convert(
        from: Currency,
        to: Currency,
        rate: CurrencyRate,
        commissionFee: Double,
    ): ConvertCurrencyResult {
        // todo extract dispatcher and cleanup
        return withContext(Dispatchers.IO) {
            val sell = from.amount + commissionFee
            val receive = from.amount * rate.rate

            val fromBalance = balancesRepository.getBalance(from.currencyType)
                ?: return@withContext ConvertCurrencyResult.Error
            val toBalance = balancesRepository.getBalance(to.currencyType)

            if (fromBalance.amount - sell < 0) {
                return@withContext ConvertCurrencyResult.FromBalanceNegative
            }

            val updatedFrom = fromBalance.copy(amount = fromBalance.amount - sell)
            val updatedTo = to.copy(amount = (toBalance?.amount ?: 0.0) + receive)
            balancesRepository.update(updatedFrom)
            balancesRepository.update(updatedTo)
            transactionsRepository.insert()
            return@withContext ConvertCurrencyResult.Success(from, to, commissionFee)
        }
    }
}
