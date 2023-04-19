package com.example.fakestore.ui.delegateAdapter.search

import androidx.appcompat.widget.SearchView
import com.example.fakestore.domain.productsEntity.ProductsItem
import io.reactivex.rxjava3.subjects.PublishSubject

interface IListSearchingResult<V: ISearchView> {
    var listener: SearchView.OnQueryTextListener ?
    var listenerClose: SearchView.OnCloseListener?



    var itemClickListener: ((V) -> Unit)?
    fun bindView(view: V)
    fun getCount(): Int
    fun setData(list: ArrayList<ProductsItem>)
}