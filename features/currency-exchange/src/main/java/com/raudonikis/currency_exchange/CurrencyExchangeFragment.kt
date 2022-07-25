package com.raudonikis.currency_exchange

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.raudonikis.common.extensions.launchWhenStarted
import com.raudonikis.common.extensions.viewBinding
import com.raudonikis.currency_exchange.databinding.FragmentCurrencyExchangeBinding
import com.raudonikis.currency_exchange.usecase.ConvertCurrencyResult
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.onEach

@AndroidEntryPoint
class CurrencyExchangeFragment : Fragment(R.layout.fragment_currency_exchange) {

    private val binding by viewBinding(FragmentCurrencyExchangeBinding::bind)
    private val currencyExchangeViewModel by viewModels<CurrencyExchangeViewModel>()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupListeners()
        setupObservers()
    }

    private fun setupObservers() {
        currencyExchangeViewModel.balances
            .onEach { balances ->
                binding.myBalancesView.updateMyBalances(balances)
            }
            .launchWhenStarted(viewLifecycleOwner)
        currencyExchangeViewModel.receiveValue
            .onEach { receiveValue ->
                binding.currencyExchangeView.updateReceiveValue(receiveValue)
            }
            .launchWhenStarted(viewLifecycleOwner)
        currencyExchangeViewModel.commissionFee
            .onEach { fee ->
                binding.currencyExchangeView.updateCommissionFee(fee)
            }
            .launchWhenStarted(viewLifecycleOwner)
        currencyExchangeViewModel.event
            .onEach { onEvent(it) }
            .launchWhenStarted(viewLifecycleOwner)
    }

    private fun setupListeners() {
        with(binding) {
            buttonSubmitCurrencyTransaction.setOnClickListener {
                currencyExchangeViewModel.onSubmitTransaction()
            }
            currencyExchangeView
                .onReceiveCurrencyTypeChanged { receiveType ->
                    currencyExchangeViewModel.onReceiveCurrencyTypeChanged(receiveType)
                }
                .onSellCurrencyTypeChanged { sellType ->
                    currencyExchangeViewModel.onSellCurrencyTypeChanged(sellType)
                }
                .onSellValueChanged { sellValue ->
                    currencyExchangeViewModel.onSellValueChanged(sellValue)
                }
        }
    }

    private fun onEvent(event: ConvertCurrencyResult) {
        // todo
    }
}
