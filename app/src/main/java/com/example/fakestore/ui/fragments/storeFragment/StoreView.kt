package com.example.fakestore.ui.fragments.storeFragment

import com.example.fakestore.domain.productsEntity.ProductsItem
import moxy.MvpView
import moxy.viewstate.strategy.AddToEndSingleStrategy
import moxy.viewstate.strategy.StateStrategyType

@StateStrategyType(AddToEndSingleStrategy::class)
interface StoreView: MvpView {

    fun init()
    fun onError(e: Throwable)
    fun updateList()
    fun updateListOnSearching()
     fun updateSearchingList()
    fun updateOnClosingSearch()

}