package com.example.fakestore.ui.fragments

import com.example.fakestore.domain.IGetCategories
import com.example.fakestore.domain.IGetProducts
import com.example.fakestore.ui.delegateAdapter.bestSellers.ProductsListPresenter
import com.example.fakestore.ui.delegateAdapter.categories.CategoryListPresenter
import com.github.terrakok.cicerone.Router
import io.reactivex.rxjava3.core.Scheduler
import moxy.MvpPresenter

class StorePresenter (
    private val categoryList: IGetCategories,
    private val productList: IGetProducts,
    private val router: Router,
    private val uiScheduler: Scheduler

): MvpPresenter<StoreView>() {

    val listCategory = CategoryListPresenter()
    val listProduct = ProductsListPresenter()

    override fun onFirstViewAttach() {
        super.onFirstViewAttach()

        viewState.init()
        loadCategories()


        listCategory.itemClickListener = {
            loadProducts(listCategory.categories[it.pos])
        }

        listProduct.onItemClickListener = {
            println("ProductClick "+listProduct.products[it.pos].toString())
        }
    }

    private fun loadCategories() {
        categoryList.getCategories().observeOn(uiScheduler).subscribe (

            { categories ->
                listCategory.categories.clear()
                listCategory.categories.addAll(categories)
                viewState.updateList()
                loadProducts(listCategory.categories[0])
            },
            {
                viewState.onError(it)
            }
        )
    }

    private fun loadProducts(category: String) {

        productList.getProductByCategory(category).observeOn(uiScheduler).subscribe (

            { products ->
                listProduct.products.clear()
                listProduct.products.addAll(products)
                viewState.updateList()
            },
            {
                viewState.onError(it)
            }
        )
    }

    fun backClicked(): Boolean{
        router.exit()
        return true
    }

}