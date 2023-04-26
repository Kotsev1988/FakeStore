package com.example.fakestore.presenter.list

import android.view.View

interface IFilterClick<V: View>  {

    var itemClickListener: ((V) -> Unit)?

}