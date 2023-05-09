package com.example.fakestore.presentation.presenter.list

import com.example.fakestore.productsEntity.Products
import com.example.fakestore.presentation.presenter.list.IListProductPresenter

class ProductsListPresenter() : IListProductPresenter {
    var products = Products()
    override var onItemClickListener: ((com.example.fakestore.presentation.view.list.ProductItemView) -> Unit)? = null
    override fun bindView(view: com.example.fakestore.presentation.view.list.ProductItemView) {

        val product = products[view.pos]

        view.setTitle(product.title)
        view.setPrice(product.price.toInt())
        view.setProductImage(product.image)

    }

    override fun getCount(): Int = products.size

}