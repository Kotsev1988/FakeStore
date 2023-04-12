package com.example.fakestore.data.room.product.entity

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey
import com.example.fakestore.data.room.categories.entity.RoomCategories
import com.example.fakestore.domain.productsEntity.Rating

@Entity(
    foreignKeys = [

        ForeignKey(
            entity = RoomCategories::class,
            parentColumns = ["category"],
            childColumns = ["category"],
            onDelete = ForeignKey.CASCADE

        )]
)
data class RoomProduct (

    @PrimaryKey var id: Int,
    var category: String,
    var description: String,
    var image: String,
    var price: Double,
    var title: String
    )