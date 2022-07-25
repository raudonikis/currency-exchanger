package com.raudonikis.currency_exchange.views

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.widget.FrameLayout
import com.mikepenz.fastadapter.FastAdapter
import com.mikepenz.fastadapter.adapters.ItemAdapter
import com.raudonikis.currency_exchange.databinding.ViewMyBalancesBinding
import com.raudonikis.currency_exchange.model.Currency
import com.raudonikis.currency_exchange.model.MyBalancesItem

class MyBalancesView(
    context: Context,
    attrs: AttributeSet? = null
) : FrameLayout(context, attrs) {

    private val binding = ViewMyBalancesBinding.inflate(LayoutInflater.from(context), this, true)

    private val myBalancesItemAdapter = ItemAdapter<MyBalancesItem>()
    private val myBalancesAdapter = FastAdapter.with(myBalancesItemAdapter)

    init {
        setupMyBalancesRecycler()
    }

    private fun setupMyBalancesRecycler() {
        binding.recyclerMyBalances.apply {
            adapter = myBalancesAdapter
        }
    }

    fun updateMyBalances(myBalances: List<Currency>) {
        myBalancesItemAdapter.set(myBalances.map { MyBalancesItem(it) }) // todo mapper
    }
}
