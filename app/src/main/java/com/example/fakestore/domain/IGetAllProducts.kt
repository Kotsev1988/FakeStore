package com.example.fakestore.domain

import com.example.fakestore.domain.productsEntity.ProductsItem
import io.reactivex.rxjava3.core.Single

interface IGetAllProducts {
    fun getAllProducts(category: String): Single<List<ProductsItem>>
}