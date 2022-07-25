package com.raudonikis.currency_exchange.views

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.widget.doOnTextChanged
import com.raudonikis.currency_exchange.databinding.ViewCurrencyExchangeSellBinding
import com.raudonikis.data.models.CurrencyType

class CurrencyExchangeSellView(
    context: Context,
    attrs: AttributeSet? = null
) : ConstraintLayout(context, attrs) {

    private val binding =
        ViewCurrencyExchangeSellBinding.inflate(LayoutInflater.from(context), this, true)

    fun onCurrencyTypeChanged(action: (item: CurrencyType) -> Unit) = apply {
        binding.sellCurrencyTypeDropdownView.onItemSelected { action(it) }
    }

    fun onSellValueChanged(action: (value: Double?) -> Unit) = apply {
        binding.textFieldCurrencySellItem.doOnTextChanged { text, _, _, _ ->
            action(text?.toString()?.toDoubleOrNull())
        }
    }
}
