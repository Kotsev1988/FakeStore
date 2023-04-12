package com.example.fakestore.ui.delegateAdapter.bestSellers

interface IListProduct<V: IItemProductView> {

    var onItemClickListener: ((V) -> Unit)?
    fun bindView(view: V)
    fun getCount(): Int
}