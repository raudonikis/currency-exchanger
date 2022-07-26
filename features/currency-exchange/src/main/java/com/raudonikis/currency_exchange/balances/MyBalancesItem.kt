package com.raudonikis.currency_exchange.balances

import android.view.LayoutInflater
import android.view.ViewGroup
import com.mikepenz.fastadapter.binding.AbstractBindingItem
import com.raudonikis.currency_exchange.R
import com.raudonikis.currency_exchange.databinding.ItemMyBalanceBinding
import com.raudonikis.currency_exchange.model.Currency

class MyBalancesItem(
    private val balance: Currency
) : AbstractBindingItem<ItemMyBalanceBinding>() {

    override val type: Int
        get() = R.id.adapter_my_balances_item_id

    override fun createBinding(inflater: LayoutInflater, parent: ViewGroup?): ItemMyBalanceBinding {
        return ItemMyBalanceBinding.inflate(inflater, parent, false)
    }

    override fun bindView(binding: ItemMyBalanceBinding, payloads: List<Any>) {
        binding.textMyBalanceItem.text = balance.toString()
    }
}
