package com.example.fakestore.presenter.list

import com.example.fakestore.domain.view.list.IItemProductView

interface IListProduct<V: IItemProductView> {

    var onItemClickListener: ((V) -> Unit)?
    fun bindView(view: V)
    fun getCount(): Int
}