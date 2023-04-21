package com.example.fakestore.ui.adapters.search

import androidx.appcompat.widget.SearchView
import com.example.fakestore.domain.productsEntity.ProductsItem
import com.example.fakestore.presenter.list.ILIstSearchingResultPresenter
import com.example.fakestore.domain.view.list.ISearchListView

class SearchListResultPresenterInFragment(
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