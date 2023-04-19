package com.example.fakestore.ui.delegateAdapter.categories

import com.example.fakestore.domain.productsEntity.Categories

class CategoryListPresenter : IListCategoryPresenter {
    var categories = Categories()
    override var itemClickListener: ((CategoryItemView) -> Unit)? = null

    override fun setData(categories: Categories) {
        this.categories = categories
    }

    override fun bindView(view: CategoryItemView) {

        var category = categories[view.pos]

        view.setText(category)

        view.loadAvatar(category)

        view.clickButton()

    }

    override fun getCount(): Int = categories.size
    override fun clear() = categories.clear()
}