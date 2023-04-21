package com.example.fakestore.domain.view.list

interface CategoryItemView: IItemView {
    fun clickButton()
    fun setText(text: String)
    fun loadAvatar(url: String)
}