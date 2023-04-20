package com.example.fakestore.ui.delegateAdapter.search

import android.view.View

interface ISearchClick<V: IClickView>  {
    var itemClickListener: ((V) -> Unit)?
    var listenerFocusChanged: View.OnFocusChangeListener?
}