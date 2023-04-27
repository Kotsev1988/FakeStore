package com.example.fakestore.presentation.presenter

import com.example.fakestore.domain.productsEntity.Products
import com.example.fakestore.presentation.presenter.list.IListProductPresenter
import com.example.fakestore.domain.view.list.ProductItemView

class ProductsListPresenter() : IListProductPresenter {
    var products = Products()
    override var onItemClickListener: ((ProductItemView) -> Unit)? = null
    override fun bindView(view: ProductItemView) {

        val product = products[view.pos]

        view.setTitle(product.title)
        view.setPrice(product.price.toInt())
        view.setProductImage(product.image)

    }

    override fun getCount(): Int = products.size

}