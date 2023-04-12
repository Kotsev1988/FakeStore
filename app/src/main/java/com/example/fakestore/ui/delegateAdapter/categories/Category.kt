package com.example.fakestore.ui.delegateAdapter.categories

import com.example.fakestore.domain.productsEntity.Categories
import com.example.fakestore.ui.delegateAdapter.DelegateAdapterItem

data class Category(val category: Categories, val presenter: IListCategoryPresenter): DelegateAdapterItem {

    override fun id(): Any  = Categories::class.java

    override fun content(): Any  = category

    override fun payload(other: Any): DelegateAdapterItem.Payloadable {
        if (other is Category){
            if (category != other.category){
                return ChangePayload.CategoryNameChanged(other.category)
            }
        }
        return DelegateAdapterItem.Payloadable.None
    }

    sealed class ChangePayload: DelegateAdapterItem.Payloadable {
        data class CategoryNameChanged (val categoryName: Categories): ChangePayload()
    }
}