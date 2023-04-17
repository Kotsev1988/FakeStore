package com.example.fakestore.ui.delegateAdapter.categories

import com.example.fakestore.domain.productsEntity.Categories

class CategoryListPresenter : IListCategoryPresenter {
    var categories = Categories()
    override var itemClickListener: ((CategoryItemView) -> Unit)? = null

    override fun bindView(view: CategoryItemView) {

        var category = categories[view.pos]

        view.setText(category)

        view.loadAvatar(category)

        view.clickButton()

    }

    override fun getCount(): Int = categories.size
}