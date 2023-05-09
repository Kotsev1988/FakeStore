package com.example.fakestore.di.product.module

import com.example.fakestore.data.GetProduct
import com.example.fakestore.data.MyCardProductsImpl
import com.example.fakestore.data.api.IStoreAPI
import com.example.fakestore.data.room.Database
import com.example.fakestore.data.room.cache.IProductCache
import com.example.fakestore.data.room.cache.room.ProductCache
import com.example.fakestore.di.product.ProductScope
import com.example.fakestore.domain.IGetProductById
import com.example.fakestore.domain.IMyCardProducts
import com.example.fakestore.network.INetworkStates
import dagger.Module
import dagger.Provides

@Module
class ProductModule {

    @Provides
    fun productCache(database: Database): IProductCache = ProductCache(database)

    @ProductScope
    @Provides
    fun productData(
        api: IStoreAPI,
        networkStatus: INetworkStates,
        productCache: IProductCache,
    ): IGetProductById =
        GetProduct(api, networkStatus, productCache)

//    @ProductScope
//    @Provides
//    fun myCardProduct(
//        db: Database
//    ): IMyCardProducts = MyCardProductsImpl(db)
}