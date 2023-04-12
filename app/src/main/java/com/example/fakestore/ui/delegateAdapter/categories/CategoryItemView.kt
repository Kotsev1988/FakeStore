package com.example.fakestore.ui.delegateAdapter.categories

interface CategoryItemView: IItemView {
    fun clickButton()
    fun setText(text: String)
    fun loadAvatar(url: String)
}