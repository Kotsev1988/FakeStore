package com.example.fakestore.ui.delegateAdapter.search

class SearchClick() : IListSearchClick {

    override var itemClickListener: ((iclickview) -> Unit)? = null
}