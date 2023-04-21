package com.example.fakestore.presenter

import android.view.View
import android.view.View.OnFocusChangeListener
import com.example.fakestore.domain.IGetCategories
import com.example.fakestore.domain.IGetProducts
import com.example.fakestore.ui.adapters.search.SearchClick
import com.example.fakestore.navigation.AndroidScreens
import com.example.fakestore.domain.view.StoreView
import com.github.terrakok.cicerone.Router
import io.reactivex.rxjava3.core.Scheduler
import moxy.MvpPresenter

class StorePresenter(
    private val categoryList: IGetCategories,
    private val productList: IGetProducts,
    private val router: Router,
    private val uiScheduler: Scheduler,

    ) : MvpPresenter<StoreView>() {

    val listCategory = CategoryListPresenter()
    val listProduct = ProductsListPresenter()


    val searchClick = SearchClick()


    override fun onFirstViewAttach() {
        super.onFirstViewAttach()

        viewState.init()
        loadCategories()

        listCategory.itemClickListener = {
            loadProducts(listCategory.categories[it.pos])
        }

        listProduct.onItemClickListener = {
            val id = listProduct.products[it.pos].id
            router.navigateTo(AndroidScreens().product(id.toString()))
        }



        searchClick.listenerFocusChanged =object : OnFocusChangeListener {
            override fun onFocusChange(p0: View?, p1: Boolean) {
                println("FocusChanged"+p1)
                if (p1){
                    router.navigateTo(AndroidScreens().search())
                }else{
                    return
                }
            }
        }
    }

    private fun loadCategories() {
        categoryList.getCategories().observeOn(uiScheduler).subscribe(

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

        productList.getProductByCategory(category).observeOn(uiScheduler).subscribe(

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

    fun backClicked(): Boolean {
        router.exit()
        return true
    }

}