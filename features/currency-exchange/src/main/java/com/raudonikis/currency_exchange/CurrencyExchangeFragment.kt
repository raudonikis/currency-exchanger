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

@AndroidEntryPoint
class CurrencyExchangeFragment : Fragment(R.layout.fragment_currency_exchange) {

    private val binding by viewBinding(FragmentCurrencyExchangeBinding::bind)
    private val currencyExchangeViewModel by viewModels<CurrencyExchangeViewModel>()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupObservers()
    }

    private fun setupObservers() {
        currencyExchangeViewModel.balances
            .onEach { balances ->
                // todo
            }
            .launchWhenStarted(viewLifecycleOwner)
    }

    private fun setupDropdowns() {
        /*with(binding) {
            // todo refactor
            val items = com.raudonikis.data.entities.CurrencyType.values()
            val adapter =
                ArrayAdapter(requireContext(), R.layout.support_simple_spinner_dropdown_item, items)
            // (dropdownCurrencySell.editText as? AutoCompleteTextView)?.setAdapter(adapter)
            // (dropdownCurrencyReceive.editText as? AutoCompleteTextView)?.setAdapter(adapter)
        }*/
    }
}
