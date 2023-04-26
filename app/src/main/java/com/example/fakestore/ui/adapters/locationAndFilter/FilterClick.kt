package com.example.fakestore.ui.adapters.locationAndFilter

import android.view.View
import com.example.fakestore.presenter.list.IFilterClick

class FilterClick() : IFilterClick<View> {

    override var itemClickListener: ((View) -> Unit)? = null
}