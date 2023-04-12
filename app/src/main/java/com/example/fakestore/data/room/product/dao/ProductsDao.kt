package com.example.fakestore.data.room.product.dao

import androidx.room.*
import com.example.fakestore.data.room.product.entity.RoomProduct
import com.example.fakestore.domain.productsEntity.ProductsItem
import com.example.fakestore.ui.delegateAdapter.categories.Category

@Dao
interface ProductsDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(products: RoomProduct)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert (vararg products: RoomProduct)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(users: List<RoomProduct>)

    @Update
    fun update(products: RoomProduct)

    @Update
    fun update(vararg products: RoomProduct)

    @Update
    fun update(product: List<RoomProduct>)

    @Delete
    fun delete(products: RoomProduct)

    @Delete
    fun delete(vararg products: RoomProduct)

    @Delete
    fun delete(user: List<RoomProduct>)

    @Query("SELECT * FROM RoomProduct")
    fun getAll(): List<RoomProduct>

    @Query("SELECT * FROM RoomProduct WHERE category = :category")
    fun findCategoryById(category: String): List<RoomProduct>
}