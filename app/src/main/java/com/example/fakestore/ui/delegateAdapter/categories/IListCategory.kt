package com.example.fakestore.ui.delegateAdapter.categories

import com.example.fakestore.domain.productsEntity.Categories

interface IListCategory<V: IItemView> {

    var itemClickListener: ((V) -> Unit)?
    fun setData(categories: Categories)
    fun bindView(view: V)
    fun getCount(): Int
    fun clear()
}