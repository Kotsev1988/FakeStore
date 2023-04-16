package com.example.fakestore.ui.delegateAdapter.bestSellers

import com.example.fakestore.domain.productsEntity.Products
import com.example.fakestore.ui.delegateAdapter.DelegateAdapterItem

data class BestSellers(val products: Products, val presenter: IListProductPresenter): DelegateAdapterItem {

    override fun id(): Any  = Products::class.java

    override fun content(): Any  = products

    override fun payload(other: Any): DelegateAdapterItem.Payloadable {
        if (other is BestSellers){
            if (products !=other.products){
                return ChangePayload.UBestSellers(other.products)
            }
        }
        return DelegateAdapterItem.Payloadable.None
    }

    sealed class ChangePayload: DelegateAdapterItem.Payloadable {
        data class UBestSellers(var newProducts: Products): ChangePayload()
    }
}