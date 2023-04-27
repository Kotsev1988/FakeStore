package com.example.fakestore.di

import com.example.fakestore.di.modules.*
import com.example.fakestore.presentation.activity.MainActivity
import com.example.fakestore.presentation.adapters.bestSellers.BestSellersProductAdapter
import com.example.fakestore.presentation.adapters.categories.CategoryHorizontalAdapter
import com.example.fakestore.presentation.fragments.SearchingFragment
import com.example.fakestore.presentation.fragments.StoreFragment
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

    fun productSubComponent(): ProductSubComponent

    fun inject(mainActivity: MainActivity)
    fun inject(storeFragment: StoreFragment)
    fun inject(searchingFragment: SearchingFragment)


    fun inject(bestSellersProductAdapter: BestSellersProductAdapter)
    fun inject(categoryHorizontalAdapter: CategoryHorizontalAdapter)
    //fun inject(myProductAdapter: MyProductsAdapter)
}