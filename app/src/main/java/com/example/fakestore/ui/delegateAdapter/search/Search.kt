package com.example.fakestore.ui.delegateAdapter.search

import com.example.fakestore.domain.productsEntity.Categories
import com.example.fakestore.domain.productsEntity.ProductsItem
import com.example.fakestore.ui.delegateAdapter.DelegateAdapterItem
import com.example.fakestore.ui.delegateAdapter.categories.Category

class Search(var results: ArrayList<ProductsItem>, val presenter: ILIstSearchingResultPresenter?): DelegateAdapterItem {

    override fun id(): Any = Search::class.java

    override fun content(): Any = SearchingContent(results, presenter)

    override fun payload(other: Any): DelegateAdapterItem.Payloadable {
        if (other is Search){
            if (results != other.results){
                return ChangePayload.SearchingChanged(other.results)
            }

        }
        return DelegateAdapterItem.Payloadable.None
    }



    inner class SearchingContent(private val results: ArrayList<ProductsItem>, val presenter: ILIstSearchingResultPresenter?){
        override fun equals(other: Any?): Boolean {
            if (other is SearchingContent){
              return results == other.results && presenter == other.presenter
            }
            return false
        }
    }

    sealed class ChangePayload: DelegateAdapterItem.Payloadable {
        data class SearchingChanged (var results: ArrayList<ProductsItem>): ChangePayload()
    }
}