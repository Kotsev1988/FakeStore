package com.example.fakestore.ui.adapters.categories

import com.example.fakestore.domain.productsEntity.Categories
import com.example.fakestore.presenter.list.IListCategoryPresenter
import com.example.fakestore.ui.adapters.DelegateAdapterItem

data class Category(val category: Categories,
                    val presenter: IListCategoryPresenter?
                    ): DelegateAdapterItem {

    override fun id(): Any  = Categories::class.java

    override fun content(): Any  = CategoryContent(category, presenter)

    override fun payload(other: Any): DelegateAdapterItem.Payloadable {
        if (other is Category){
            if (category != other.category){
                return ChangePayload.CategoryNameChanged(other.category)
            }

        }
        return DelegateAdapterItem.Payloadable.None
    }

    inner class CategoryContent(val category: Categories,
                                val presenter: IListCategoryPresenter?){
        override fun equals(other: Any?): Boolean {
            if (other is CategoryContent){
                return category == other.category && presenter == other.presenter
            }
            return false
        }
    }

    sealed class ChangePayload: DelegateAdapterItem.Payloadable {
        data class CategoryNameChanged (val categoryName: Categories): ChangePayload()
    }
}