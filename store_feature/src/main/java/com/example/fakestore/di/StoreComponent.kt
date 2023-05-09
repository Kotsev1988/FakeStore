package com.example.fakestore.di

import com.example.fakestore.di.modules.StoreModule
import com.example.fakestore.di.scopes.StoreScope
import com.example.fakestore.presentation.adapters.bestSellers.BestSellersProductAdapter
import com.example.fakestore.presentation.adapters.categories.CategoryHorizontalAdapter
import com.example.fakestore.presentation.fragments.StoreFragment
import dagger.Component

@StoreScope
@Component(
    dependencies = [BaseComponent::class],
    modules = [StoreModule::class]
)
interface StoreComponent {

    fun productSubComponent(): ProductSubComponent



    fun inject(storeFragment: StoreFragment)
    fun inject(bestSellersProductAdapter: BestSellersProductAdapter)
    fun inject(categoryHorizontalAdapter: CategoryHorizontalAdapter)

}