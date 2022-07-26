package com.raudonikis.currency_exchange.views

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.widget.ArrayAdapter
import android.widget.AutoCompleteTextView
import android.widget.FrameLayout
import com.raudonikis.currency_exchange.R
import com.raudonikis.currency_exchange.databinding.ViewCurrencyTypeDropdownBinding
import com.raudonikis.common.model.CurrencyType

class CurrencyTypeDropdownView(
    context: Context,
    attrs: AttributeSet? = null
) : FrameLayout(context, attrs) {

    private val binding =
        ViewCurrencyTypeDropdownBinding.inflate(LayoutInflater.from(context), this, true)

    private val dropdown: AutoCompleteTextView
        get() = binding.autoCompleteCurrencyTypeDropdown

    private var onItemSelected: (item: CurrencyType) -> Unit = {}

    init {
        setupDropdownAdapter()
    }

    private fun setupDropdownAdapter() {
        val adapter = ArrayAdapter(
            context,
            R.layout.support_simple_spinner_dropdown_item,
            CurrencyType.values()
        )
        dropdown.apply {
            setAdapter(adapter)
            setOnItemClickListener { _, _, index, _ ->
                adapter.getItem(index)?.let { item ->
                    onItemSelected(item)
                }
            }
        }
    }

    fun onItemSelected(action: (item: CurrencyType) -> Unit) = apply {
        onItemSelected = action
    }
}
