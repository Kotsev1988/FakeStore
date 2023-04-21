package com.example.fakestore.presenter.list

import androidx.appcompat.widget.SearchView
import com.example.fakestore.domain.productsEntity.ProductsItem
import com.example.fakestore.domain.view.list.ISearchView

interface IListSearchingResult<V: ISearchView> {
    var listener: SearchView.OnQueryTextListener ?
    var listenerClose: SearchView.OnCloseListener?



    var itemClickListener: ((V) -> Unit)?
    fun bindView(view: V)
    fun getCount(): Int
    fun setData(list: ArrayList<ProductsItem>)
}