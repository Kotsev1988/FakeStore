package com.example.fakestore.presentation.presenter.list

interface IListProduct<V: com.example.fakestore.presentation.view.list.IItemProductView> {

    var onItemClickListener: ((V) -> Unit)?
    fun bindView(view: V)
    fun getCount(): Int
}