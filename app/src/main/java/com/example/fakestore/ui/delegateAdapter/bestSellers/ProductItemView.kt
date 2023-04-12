package com.example.fakestore.ui.delegateAdapter.bestSellers

interface ProductItemView: IItemProductView {
    fun setTitle(title: String)
    fun setPrice(price: Int)
    fun setProductImage(url: String)
}