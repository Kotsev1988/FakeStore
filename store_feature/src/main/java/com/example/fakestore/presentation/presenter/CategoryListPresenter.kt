package com.example.fakestore.presentation.presenter

import com.example.fakestore.presentation.presenter.list.IListCategoryPresenter

class CategoryListPresenter : IListCategoryPresenter {
    var categories = com.example.fakestore.productsEntity.Categories()
    override var itemClickListener: ((com.example.fakestore.presentation.view.list.CategoryItemView) -> Unit)? = null

    override fun setData(categories: com.example.fakestore.productsEntity.Categories) {
        this.categories = categories
    }

    override fun bindView(view: com.example.fakestore.presentation.view.list.CategoryItemView) {

        var category = categories[view.pos]

        view.setText(category)

        view.loadAvatar(category)

        view.clickButton()

    }

    override fun getCount(): Int = categories.size
    override fun clear() = categories.clear()
}