package com.example.fakestore.presentation.view

import com.example.fakestore.productsEntity.Products
import com.example.fakestore.productsEntity.ProductsLike
import moxy.MvpView
import moxy.viewstate.strategy.AddToEndSingleStrategy
import moxy.viewstate.strategy.SkipStrategy
import moxy.viewstate.strategy.StateStrategyType

@StateStrategyType(AddToEndSingleStrategy::class)
interface StoreView: MvpView {

    fun init()
    fun onError(e: Throwable)
    fun updateList()
    @StateStrategyType(SkipStrategy::class)
    fun showBottomDialog()
    @StateStrategyType(SkipStrategy::class)
    fun filter()
    fun setLocation(city: String)
    @StateStrategyType(SkipStrategy::class)
     fun goToProduct(id: Int)
    fun setProducts(products: Products)
    fun updateLikesView(productsLikes: ProductsLike)
    @StateStrategyType(SkipStrategy::class)
    fun navigateToSearchFragment()
}