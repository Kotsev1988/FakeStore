package com.example.fakestore.ui.adapters.search

import android.view.View
import com.example.fakestore.presenter.list.IListSearchClick
import com.example.fakestore.domain.view.list.IClickView

class SearchClick() : IListSearchClick {

    override var itemClickListener: ((IClickView) -> Unit)? = null
    override var listenerFocusChanged: View.OnFocusChangeListener? = null
}