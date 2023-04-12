package com.example.fakestore.ui.delegateAdapter.categories

interface IListCategory<V: IItemView> {

    var itemClickListener: ((V) -> Unit)?
    fun bindView(view: V)
    fun getCount(): Int
}