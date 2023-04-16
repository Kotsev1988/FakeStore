package com.example.fakestore.domain

import com.example.fakestore.domain.productsEntity.ProductsItem
import io.reactivex.rxjava3.core.Single

interface IGetProductById {
    fun getProductById(id: String): Single<ProductsItem>
}