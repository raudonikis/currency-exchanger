package com.raudonikis.currency_exchange

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.raudonikis.common.extensions.launchWhenStarted
import com.raudonikis.common.extensions.viewBinding
import com.raudonikis.currency_exchange.databinding.FragmentCurrencyExchangeBinding
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.onEach
import timber.log.Timber

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
                // todo show list of balances
            }
            .launchWhenStarted(viewLifecycleOwner)
        currencyExchangeViewModel.rate
            .onEach { rate ->
                Timber.d("New rate calculated -> $rate")
                // todo update the receive value?
            }
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
        }
    }
}
