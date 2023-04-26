package com.example.fakestore.ui.adapters.locationAndFilter

import android.view.View
import com.example.fakestore.presenter.list.IFilterClick
import com.example.fakestore.ui.adapters.DelegateAdapterItem

data class LocationAndFilter(

    val location: String,
    val presenter: IFilterClick<View>?

): DelegateAdapterItem {

    override fun id(): Any = LocationAndFilter::class.toString()

    override fun content(): Any = location
}