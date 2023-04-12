package com.example.fakestore.data.room.cache.room


import com.example.fakestore.data.room.Database
import com.example.fakestore.data.room.cache.ICategoriesCache
import com.example.fakestore.data.room.categories.entity.RoomCategories
import io.reactivex.rxjava3.core.Single
import io.reactivex.rxjava3.schedulers.Schedulers

class CategoriesCache(private val db: Database): ICategoriesCache {
    override fun insertCategoriesToDB(categories: List<String>): Single<List<String>> =
        Single.fromCallable {
            val roomCategories = categories.map { category ->
                RoomCategories(category = category)
            }
            db.categoriesDao.insert(roomCategories)
            categories
        }.subscribeOn(Schedulers.io())


    override fun getCategoriesFromCache(): Single<List<String>> = Single.fromCallable{
        db.categoriesDao.getAll()
    }

}