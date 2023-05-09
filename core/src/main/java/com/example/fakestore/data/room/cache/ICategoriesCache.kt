package com.example.fakestore.data.room.cache

import io.reactivex.rxjava3.core.Single

interface ICategoriesCache {

    fun insertCategoriesToDB(categories: List<String>): Single<List<String>>
    fun getCategoriesFromCache(): Single<List<String>>
}