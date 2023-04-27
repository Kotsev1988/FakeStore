package com.example.fakestore.presentation.presenter

import com.example.fakestore.domain.productsEntity.Products
import com.example.fakestore.domain.view.list.IMyProductsView
import com.example.fakestore.presentation.presenter.list.IListMyProducts

class MyCardProductsPresenter(): IListMyProducts {
    override var itemClickListenerIncrease: ((IMyProductsView) -> Unit)? = null
    override var itemClickListenerDecrease: ((IMyProductsView) -> Unit)? = null
    override var itemClickListenerDelete: ((IMyProductsView) -> Unit)? = null

    var myProducts  = Products()

    override fun bindView(view: IMyProductsView) {
        val product = myProducts[view.pos]

        view.setText(product.title)
        view.setPrice(product.price.toString())
        view.loadAvatar(product.image)
        view.setCounter(product.count.toString())
    }

    override fun getCount(): Int = myProducts.size
}