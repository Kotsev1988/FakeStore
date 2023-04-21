package com.example.fakestore.presenter.list

import android.view.View
import com.example.fakestore.domain.view.list.IClickView

interface ISearchClick<V: IClickView>  {
    var itemClickListener: ((V) -> Unit)?
    var listenerFocusChanged: View.OnFocusChangeListener?
}