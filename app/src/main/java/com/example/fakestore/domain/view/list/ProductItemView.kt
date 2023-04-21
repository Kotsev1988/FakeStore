package com.example.fakestore.domain.view.list

interface ProductItemView: IItemProductView {
    fun setTitle(title: String)
    fun setPrice(price: Int)
    fun setProductImage(url: String)
}