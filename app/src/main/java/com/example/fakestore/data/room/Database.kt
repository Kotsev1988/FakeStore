package com.example.fakestore.data.room

import android.content.Context
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.fakestore.data.room.categories.dao.CategoriesDao
import com.example.fakestore.data.room.categories.entity.RoomCategories
import com.example.fakestore.data.room.product.dao.ProductsDao
import com.example.fakestore.data.room.product.entity.RoomProduct

@androidx.room.Database(entities = [RoomCategories::class, RoomProduct::class], version = 1)
abstract class Database : RoomDatabase(){

    abstract val categoriesDao: CategoriesDao
    abstract val productsDao: ProductsDao

    companion object{
        const val  DB_NAME = "database.db"
        private var instance: Database? = null

        fun getInstance() = instance ?: throw RuntimeException("Database has not " +
                "been created. Please call create(context)")

        fun create(context: Context){
            if (instance == null){
                instance = Room.databaseBuilder(context, Database::class.java, DB_NAME).build()
            }
        }

    }
}