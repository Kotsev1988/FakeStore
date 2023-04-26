package com.example.fakestore.domain.view.list

interface IMyProductsView: IProductsView {
    fun setText(text: String)
    fun setPrice(price: String)
    fun loadAvatar(url: String)
    fun setCounter(counter: String)
}