package com.example.fakestore.data.room

import androidx.room.RoomDatabase
import com.example.fakestore.data.room.categories.dao.CategoriesDao
import com.example.fakestore.data.room.categories.entity.RoomCategories
import com.example.fakestore.data.room.products.dao.ProductsDao
import com.example.fakestore.data.room.products.entity.RoomProducts

@androidx.room.Database(entities = [RoomCategories::class, RoomProducts::class], version = 1)
abstract class Database : RoomDatabase() {

    abstract val categoriesDao: CategoriesDao
    abstract val productsDao: ProductsDao

    companion object {
        const val DB_NAME = "database.db"
    }
}