package com.example.fakestore.ui.fragments.searchingFragment.search

import androidx.appcompat.widget.SearchView
import com.example.fakestore.domain.productsEntity.ProductsItem

interface IListSearchingResult<V: ISearchView> {
    var listener: SearchView.OnQueryTextListener ?
    var listenerClose: SearchView.OnCloseListener?



    var itemClickListener: ((V) -> Unit)?
    fun bindView(view: V)
    fun getCount(): Int
    fun setData(list: ArrayList<ProductsItem>)
}