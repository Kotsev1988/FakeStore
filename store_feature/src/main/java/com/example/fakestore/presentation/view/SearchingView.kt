package com.example.fakestore.presentation.view

import moxy.MvpView
import moxy.viewstate.strategy.AddToEndSingleStrategy
import moxy.viewstate.strategy.StateStrategyType

@StateStrategyType(AddToEndSingleStrategy::class)
interface SearchingView: MvpView {

    fun init()
    fun updateSearchingList()
    fun updateListOnSearching()
     fun onError(it: Throwable)
}