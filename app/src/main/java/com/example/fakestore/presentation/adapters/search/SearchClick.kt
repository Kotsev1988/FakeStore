package com.example.fakestore.presentation.adapters.search

import android.view.View
import com.example.fakestore.presentation.presenter.list.IListSearchClick
import com.example.fakestore.domain.view.list.IClickView

class SearchClick() : IListSearchClick {

    override var itemClickListener: ((IClickView) -> Unit)? = null
    override var listenerFocusChanged: View.OnFocusChangeListener? = null
}