package com.example.fakestore.domain

import com.example.fakestore.domain.productsEntity.Products
import com.example.fakestore.domain.productsEntity.ProductsItem
import io.reactivex.rxjava3.core.Single

interface IGetProducts {

    fun getProductByCategory(category: String): Single<List<ProductsItem>>
}