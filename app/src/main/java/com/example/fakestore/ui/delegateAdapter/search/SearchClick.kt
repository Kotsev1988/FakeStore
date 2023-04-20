package com.example.fakestore.ui.delegateAdapter.search

import android.view.View

class SearchClick() : IListSearchClick {

    override var itemClickListener: ((IClickView) -> Unit)? = null
    override var listenerFocusChanged: View.OnFocusChangeListener? = null
}