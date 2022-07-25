package com.raudonikis.currency_exchange.model

import android.view.LayoutInflater
import android.view.ViewGroup
import com.mikepenz.fastadapter.binding.AbstractBindingItem
import com.raudonikis.currency_exchange.databinding.ItemMyBalanceBinding

class MyBalancesItem(
    private val balance: Currency
) : AbstractBindingItem<ItemMyBalanceBinding>() {

    override val type: Int
        get() = 1 // todo

    override fun createBinding(inflater: LayoutInflater, parent: ViewGroup?): ItemMyBalanceBinding {
        return ItemMyBalanceBinding.inflate(inflater, parent, false)
    }

    override fun bindView(binding: ItemMyBalanceBinding, payloads: List<Any>) {
        binding.textMyBalanceItem.text = balance.toString()
    }
}
