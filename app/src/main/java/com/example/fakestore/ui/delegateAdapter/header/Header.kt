package com.example.fakestore.ui.delegateAdapter.header

import com.example.fakestore.domain.productsEntity.Categories
import com.example.fakestore.ui.delegateAdapter.DelegateAdapterItem
import com.example.fakestore.ui.delegateAdapter.categories.Category

class Header(val header: String): DelegateAdapterItem {
    override fun id(): Any = Header::class.java

    override fun content(): Any = header

    override fun payload(other: Any): DelegateAdapterItem.Payloadable {
        if (other is Header){
            if (header != other.header){
                return ChangePayload.CategoryNameChanged(other.header)
            }

        }
        return DelegateAdapterItem.Payloadable.None
    }

    sealed class ChangePayload: DelegateAdapterItem.Payloadable {
        data class CategoryNameChanged (val categoryName: String): ChangePayload()
    }
}