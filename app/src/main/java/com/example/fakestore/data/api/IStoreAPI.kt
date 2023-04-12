package com.example.fakestore.data.api

import com.example.fakestore.domain.productsEntity.Categories
import com.example.fakestore.domain.productsEntity.Products
import com.example.fakestore.domain.productsEntity.ProductsItem
import io.reactivex.rxjava3.core.Single
import retrofit2.http.GET
import retrofit2.http.Path

interface IStoreAPI {

    @GET("products/categories")
    fun getAllCategories(): Single<Categories>

    @GET("products")
    fun getAllProducts(): Single<Products>

    @GET("/products/category/{category}")
    fun getCategory(@Path("category") category: String): Single<Products>


}