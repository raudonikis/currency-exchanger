package com.raudonikis.currency_exchange

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.raudonikis.common.extensions.enabledIf
import com.raudonikis.common.extensions.launchWhenStarted
import com.raudonikis.common.extensions.viewBinding
import com.raudonikis.currency_exchange.convert.ConvertCurrencyResult
import com.raudonikis.currency_exchange.databinding.FragmentCurrencyExchangeBinding
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
        currencyExchangeViewModel.conversionResult
            .onEach { onConversionResult(it) }
            .launchWhenStarted(viewLifecycleOwner)
        currencyExchangeViewModel.isValid
            .onEach { isValid -> binding.buttonSubmitCurrencyTransaction.enabledIf { isValid } }
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

    private fun onConversionResult(result: ConvertCurrencyResult) {
        context?.let { context ->
            MaterialAlertDialogBuilder(context)
                .setNeutralButton(android.R.string.ok) { dialog, _ ->
                    dialog.dismiss()
                }
                .apply {
                    when (result) {
                        is ConvertCurrencyResult.Success -> {
                            setTitle(R.string.dialog_currency_converted_title)
                            setMessage(
                                getString(
                                    R.string.dialog_currency_converted_message,
                                    result.from,
                                    result.to,
                                    result.fee,
                                )
                            )
                        }
                        is ConvertCurrencyResult.FromBalanceNegative -> {
                            setTitle(R.string.dialog_currency_converted_failure_title)
                            setMessage(R.string.dialog_currency_converted_negative_failure_message)
                        }
                        is ConvertCurrencyResult.Error ->
                            setTitle(R.string.dialog_currency_converted_failure_title)
                    }
                    show()
                }
        }
    }
}
