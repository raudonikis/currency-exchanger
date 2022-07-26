package com.raudonikis.currency_exchange.convert

import com.raudonikis.common.coroutines.CoroutinesDispatcherProvider
import com.raudonikis.currency_exchange.balances.CurrencyBalancesRepository
import com.raudonikis.currency_exchange.model.Currency
import com.raudonikis.currency_exchange.repo.CurrencyTransactionsRepository
import kotlinx.coroutines.withContext
import javax.inject.Inject

class ConvertCurrencyUseCase @Inject constructor(
    private val transactionsRepository: CurrencyTransactionsRepository,
    private val balancesRepository: CurrencyBalancesRepository,
    private val dispatchers: CoroutinesDispatcherProvider
) {

    suspend fun convert(
        from: Currency,
        to: Currency,
        rate: Currency,
        commissionFee: Double,
    ): ConvertCurrencyResult {
        // todo cleanup
        return withContext(dispatchers.io) {
            val sell = from.amount + commissionFee
            val receive = from.amount * rate.amount

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
            return@withContext ConvertCurrencyResult.Success(
                from,
                to,
                Currency(currencyType = from.currencyType, amount = commissionFee)
            )
        }
    }
}
