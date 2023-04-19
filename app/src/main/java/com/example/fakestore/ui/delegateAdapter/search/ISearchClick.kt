package com.example.fakestore.ui.delegateAdapter.search

import androidx.appcompat.widget.SearchView

interface ISearchClick<V: iclickview>  {
    var itemClickListener: ((V) -> Unit)?
}