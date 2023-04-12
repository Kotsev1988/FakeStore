package com.example.fakestore.data

import com.example.fakestore.data.api.IStoreAPI
import com.example.fakestore.data.api.StoreAPI
import com.example.fakestore.data.room.Database
import com.example.fakestore.data.room.cache.ICategoriesCache
import com.example.fakestore.data.room.cache.room.CategoriesCache
import com.example.fakestore.data.room.categories.entity.RoomCategories
import com.example.fakestore.domain.IGetCategories
import com.example.fakestore.domain.productsEntity.Categories
import com.example.fakestore.ui.network.INetworkStates
import io.reactivex.rxjava3.core.Single
import io.reactivex.rxjava3.schedulers.Schedulers

class GetCategoriesImpl(private val api: StoreAPI, private val networkStatus: INetworkStates, private val categoriesCache: ICategoriesCache): IGetCategories {

    override fun getCategories(): Single<List<String>> = networkStatus.isOnlineSingle().flatMap { isOnline ->
        if (isOnline){
            api.getAllCategories().flatMap { categories ->
                categoriesCache.insertCategoriesToDB(categories).map {
                    it
                }
            }
        }else{
            categoriesCache.getCategoriesFromCache()
        }
    }.subscribeOn(Schedulers.io())


//api.getAllCategories().subscribeOn(Schedulers.io())

}