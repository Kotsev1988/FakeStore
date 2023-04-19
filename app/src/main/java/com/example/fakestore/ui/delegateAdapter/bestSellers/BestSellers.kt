package com.example.fakestore.ui.delegateAdapter.bestSellers

import com.example.fakestore.domain.productsEntity.Categories
import com.example.fakestore.domain.productsEntity.Products
import com.example.fakestore.ui.delegateAdapter.DelegateAdapterItem
import com.example.fakestore.ui.delegateAdapter.categories.IListCategoryPresenter

data class BestSellers(
    val products: Products,
    val presenter: IListProductPresenter?
                       )
    : DelegateAdapterItem {

    override fun id(): Any  = Products::class.java

    override fun content(): Any  = BestSellersContent(products, presenter)

    override fun payload(other: Any): DelegateAdapterItem.Payloadable {
        if (other is BestSellers){
            if (products !=other.products){
                return ChangePayload.UBestSellers(other.products)
            }
        }
        return DelegateAdapterItem.Payloadable.None
    }

    inner class BestSellersContent(val products: Products,
                                   val presenter: IListProductPresenter?){
        override fun equals(other: Any?): Boolean {
            if (other is BestSellersContent){
                return products == other.products && presenter == other.presenter
            }
            return false
        }
    }

    sealed class ChangePayload: DelegateAdapterItem.Payloadable {
        data class UBestSellers(var newProducts: Products): ChangePayload()
    }
}