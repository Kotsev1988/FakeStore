package com.example.fakestore.presentation.adapters.locationAndFilter

import android.view.View
import com.example.fakestore.presentation.presenter.list.IFilterClick

class FilterClick() : IFilterClick<View> {

    override var itemClickListener: ((View) -> Unit)? = null
}