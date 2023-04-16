package com.example.fakestore.ui.fragments.storeFragment

import moxy.MvpView
import moxy.viewstate.strategy.AddToEndSingleStrategy
import moxy.viewstate.strategy.StateStrategyType

@StateStrategyType(AddToEndSingleStrategy::class)
interface StoreView: MvpView {

    fun init()
    fun onError(e: Throwable)
    fun updateList()

}