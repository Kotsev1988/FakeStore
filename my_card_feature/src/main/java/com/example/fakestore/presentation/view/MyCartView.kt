package com.example.fakestore.presentation.view

import moxy.MvpView
import moxy.viewstate.strategy.AddToEndSingleStrategy
import moxy.viewstate.strategy.StateStrategyType

@StateStrategyType(AddToEndSingleStrategy::class)
interface MyCartView: MvpView {

    fun updateList()
    fun setTotalPrice(totalPrice: String)
    fun updatePrices()
}