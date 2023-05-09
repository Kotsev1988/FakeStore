package com.example.fakestore.presentation.view

import moxy.MvpView
import moxy.viewstate.strategy.AddToEndSingleStrategy
import moxy.viewstate.strategy.SkipStrategy
import moxy.viewstate.strategy.StateStrategyType

@StateStrategyType(AddToEndSingleStrategy::class)
interface StoreView: MvpView {

    fun init()
    fun onError(e: Throwable)
    fun updateList()
    fun showBottomDialog()
    fun filter()
    fun setLocation(city: String)
    @StateStrategyType(SkipStrategy::class)
     fun goToProduct(id: Int)
}