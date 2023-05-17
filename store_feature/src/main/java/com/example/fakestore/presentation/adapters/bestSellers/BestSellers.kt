package com.example.fakestore.presentation.adapters.bestSellers

import com.example.fakestore.presentation.adapters.DelegateAdapterItem
import com.example.fakestore.presentation.presenter.list.IListProductPresenter
import com.example.fakestore.productsEntity.Products
import com.example.fakestore.productsEntity.ProductsLike

data class BestSellers(
    val products: Products,
    val productLikes: ProductsLike,
    val presenter: IListProductPresenter?
                       )
    : DelegateAdapterItem {

    override fun id(): Any  = Products::class.java

    override fun content(): Any  = BestSellersContent(products, productLikes, presenter)

    override fun payload(other: Any): DelegateAdapterItem.Payloadable {
        if (other is BestSellers){
            if (products !=other.products){
                return ChangePayload.UBestSellers(other.products)
            }
        }
        return DelegateAdapterItem.Payloadable.None
    }

    inner class BestSellersContent(val products: Products,
                                   val productLikes: ProductsLike,
                                   val presenter: IListProductPresenter?){
        override fun equals(other: Any?): Boolean {
            if (other is BestSellersContent){
                return products == other.products && productLikes == other.productLikes && presenter == other.presenter
            }
            return false
        }
    }

    sealed class ChangePayload: DelegateAdapterItem.Payloadable {
        data class UBestSellers(var newProducts: Products): ChangePayload()
        data class UBestSellersLikes(var productLikes: ProductsLike): ChangePayload()
    }
}