package com.example.fakestore.data

import com.example.fakestore.data.api.IStoreAPI
import com.example.fakestore.data.api.StoreAPI
import com.example.fakestore.data.room.Database
import com.example.fakestore.data.room.cache.IProductsCache
import com.example.fakestore.data.room.product.entity.RoomProduct
import com.example.fakestore.domain.IGetProducts
import com.example.fakestore.domain.productsEntity.Products
import com.example.fakestore.domain.productsEntity.ProductsItem
import com.example.fakestore.domain.productsEntity.Rating
import com.example.fakestore.ui.network.INetworkStates
import io.reactivex.rxjava3.core.Single
import io.reactivex.rxjava3.schedulers.Schedulers

class GetProductsImpl(
    private val api: StoreAPI,
    private val networkStatus: INetworkStates,
    private val productCache: IProductsCache
) : IGetProducts {

    override fun getProductByCategory(category: String): Single<List<ProductsItem>> =
        networkStatus.isOnlineSingle().flatMap { isOnline ->

            if (isOnline) {
                api.getProductByCategory(category).flatMap { products ->
                   productCache.insertProductsToDB(category, products)

                } ?: Single.error<Products>(RuntimeException("no category "))
                    .subscribeOn(Schedulers.io())
            } else {
                productCache.getProductsFromCache(category)
            }.subscribeOn(Schedulers.io())

        }


    //api.getProductByCategory(category)
}