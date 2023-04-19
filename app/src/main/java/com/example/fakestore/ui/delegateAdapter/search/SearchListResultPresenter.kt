package com.example.fakestore.ui.delegateAdapter.search

import androidx.appcompat.widget.SearchView
import com.example.fakestore.domain.productsEntity.ProductsItem
import io.reactivex.rxjava3.subjects.PublishSubject

class SearchListResultPresenter(
) : ILIstSearchingResultPresenter {
    var results = arrayListOf<ProductsItem>()
    override var itemClickListener: ((ISearchListView) -> Unit)? = null

    override var listener: SearchView.OnQueryTextListener? =null
    override var listenerClose: SearchView.OnCloseListener? = null


    override fun bindView(view: ISearchListView) {
        var result = results[view.pos]

        view.setText(result.title)
    }

    override fun getCount(): Int = results.size

    override fun setData(list: ArrayList<ProductsItem>) {
        results.clear()
        results.addAll(list)
    }
}