package com.raudonikis.currency_exchange.views

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import androidx.constraintlayout.widget.ConstraintLayout
import com.raudonikis.currency_exchange.databinding.ViewCurrencyExchangeReceiveBinding

class CurrencyExchangeReceiveView(
    context: Context,
    attrs: AttributeSet? = null
) : ConstraintLayout(context, attrs) {

    private val binding =
        ViewCurrencyExchangeReceiveBinding.inflate(LayoutInflater.from(context), this, true)
}
