package com.raudonikis.currency_exchange.views

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import androidx.constraintlayout.widget.ConstraintLayout
import com.raudonikis.currency_exchange.databinding.ViewCurrencyExchangeBinding
import com.raudonikis.data.models.CurrencyType

class CurrencyExchangeView(
    context: Context,
    attrs: AttributeSet? = null
) : ConstraintLayout(context, attrs) {

    private val binding =
        ViewCurrencyExchangeBinding.inflate(LayoutInflater.from(context), this, true)

    fun onSellCurrencyTypeChanged(action: (item: CurrencyType) -> Unit) = apply {
        binding.currencyExchangeSell.onCurrencyTypeChanged(action)
    }

    fun onReceiveCurrencyTypeChanged(action: (item: CurrencyType) -> Unit) = apply {
        binding.currencyExchangeReceive.onCurrencyTypeChanged(action)
    }
}
