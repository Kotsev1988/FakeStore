package com.example.fakestore.ui.fragments.searchingFragment.search

import com.example.fakestore.domain.productsEntity.ProductsItem
import com.example.fakestore.ui.delegateAdapter.DelegateAdapterItem

class SearchTest(var results: ArrayList<ProductsItem>, val presenter: ILIstSearchingResultPresenter?): DelegateAdapterItem {

    override fun id(): Any = SearchTest::class.java

    override fun content(): Any = SearchingContentTest(results, presenter)

    override fun payload(other: Any): DelegateAdapterItem.Payloadable {
        if (other is SearchTest){
            if (results != other.results){
                return ChangePayload.SearchingChangedTest(other.results)
            }
        }
        return DelegateAdapterItem.Payloadable.None
    }

    inner class SearchingContentTest(private val results: ArrayList<ProductsItem>, val presenter: ILIstSearchingResultPresenter?){
        override fun equals(other: Any?): Boolean {
            if (other is SearchingContentTest){
              return results == other.results && presenter == other.presenter
            }
            return false
        }
    }

    sealed class ChangePayload: DelegateAdapterItem.Payloadable {
        data class SearchingChangedTest (var results: ArrayList<ProductsItem>): ChangePayload()
    }
}