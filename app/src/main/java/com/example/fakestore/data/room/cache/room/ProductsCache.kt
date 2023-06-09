package com.example.fakestore.data.room.cache.room

import com.example.fakestore.data.room.Database
import com.example.fakestore.data.room.cache.IProductsCache
import com.example.fakestore.data.room.products.entity.RoomProducts
import com.example.fakestore.domain.productsEntity.ProductsItem
import com.example.fakestore.domain.productsEntity.Rating
import io.reactivex.rxjava3.core.Single
import io.reactivex.rxjava3.schedulers.Schedulers

class ProductsCache(private val db: Database): IProductsCache {


    override fun insertProductsToDB(category: String, products: List<ProductsItem>) =
        Single.fromCallable {

            val productsRoom = products.map { products ->
                RoomProducts(products.id,
                    products.category,
                    products.description,
                    products.image,
                    products.price,
                    products.title)
            }
            db.productsDao.insert(productsRoom)
            products
        }.subscribeOn(Schedulers.io())


    override fun getProductsFromCache(category: String): Single<List<ProductsItem>> =
        Single.fromCallable {
            val roomProductCategory = db.categoriesDao.findByCategoryId(category)
            db.productsDao.findCategoryById(roomProductCategory.category).map {
                ProductsItem(it.category,
                    it.description,
                    it.id,
                    it.image,
                    it.price,
                    Rating(0, 0.0),
                    it.title)
            }
        }.subscribeOn(Schedulers.io())
}