package com.example.fakestore.domain.view

import moxy.MvpView
import moxy.viewstate.strategy.AddToEndSingleStrategy
import moxy.viewstate.strategy.StateStrategyType

@StateStrategyType(AddToEndSingleStrategy::class)
interface StoreView: MvpView {

    fun init()
    fun onError(e: Throwable)
    fun updateList()
    fun showBottomDialog()
    fun filter()
    fun setLocation(city: String)
}