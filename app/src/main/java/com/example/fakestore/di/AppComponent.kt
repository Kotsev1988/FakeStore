package com.example.fakestore.di

import com.example.fakestore.di.modules.*
import com.example.fakestore.ui.MainActivity
import com.example.fakestore.ui.delegateAdapter.bestSellers.BestSellersProductAdapter
import com.example.fakestore.ui.delegateAdapter.categories.CategoryHorizontalAdapter
import com.example.fakestore.ui.fragments.productFragment.ProductFragment
import com.example.fakestore.ui.fragments.storeFragment.StoreFragment
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(
    modules = [
        AppModule::class,
        ApiModule::class,
        CiceroneModule::class,
        DatabaseModule::class,
        DataModule::class,
        LoadImageModule::class
    ]
)
interface AppComponent {

    fun inject(mainActivity: MainActivity)
    fun inject(storeFragment: StoreFragment)
    fun inject(productFragment: ProductFragment)

    fun inject(bestSellersProductAdapter: BestSellersProductAdapter)
    fun inject(categoryHorizontalAdapter: CategoryHorizontalAdapter)
}