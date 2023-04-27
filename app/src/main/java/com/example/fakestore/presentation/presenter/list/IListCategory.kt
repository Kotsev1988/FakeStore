package com.example.fakestore.presentation.presenter.list

import com.example.fakestore.domain.productsEntity.Categories
import com.example.fakestore.domain.view.list.IItemView

interface IListCategory<V: IItemView> {

    var itemClickListener: ((V) -> Unit)?
    fun setData(categories: Categories)
    fun bindView(view: V)
    fun getCount(): Int
    fun clear()
}