package com.example.fakestore.presenter.list

import com.example.fakestore.domain.view.list.IMyProductsView

interface IListCardProducts<V: IMyProductsView> {

    var itemClickListenerIncrease: ((V) -> Unit)?
    var itemClickListenerDecrease: ((V) -> Unit)?
    var itemClickListenerDelete: ((V) -> Unit)?
    fun bindView(view: V)
    fun getCount(): Int


}