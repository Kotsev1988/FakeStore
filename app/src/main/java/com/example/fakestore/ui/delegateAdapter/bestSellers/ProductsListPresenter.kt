package com.example.fakestore.ui.delegateAdapter.bestSellers

import com.example.fakestore.domain.productsEntity.Products

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