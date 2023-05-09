package com.example.fakestore.presentation.presenter.list

interface IListCategory<V: com.example.fakestore.presentation.view.list.IItemView> {

    var itemClickListener: ((V) -> Unit)?
    fun setData(categories: com.example.fakestore.productsEntity.Categories)
    fun bindView(view: V)
    fun getCount(): Int
    fun clear()
}