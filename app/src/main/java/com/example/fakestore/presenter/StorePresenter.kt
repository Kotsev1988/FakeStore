package com.example.fakestore.presenter

import android.view.View
import android.view.View.OnFocusChangeListener
import com.example.fakestore.domain.IGetCategories
import com.example.fakestore.domain.IGetProducts
import com.example.fakestore.domain.productsEntity.Products
import com.example.fakestore.domain.view.StoreView
import com.example.fakestore.navigation.AndroidScreens
import com.example.fakestore.ui.adapters.locationAndFilter.FilterClick
import com.example.fakestore.ui.adapters.search.SearchClick
import com.github.terrakok.cicerone.Router
import io.reactivex.rxjava3.core.Observable
import io.reactivex.rxjava3.core.Scheduler
import io.reactivex.rxjava3.schedulers.Schedulers
import moxy.MvpPresenter
import java.util.*

class StorePresenter(
    private val categoryList: IGetCategories,
    private val productList: IGetProducts,
    private val router: Router,
    private val uiScheduler: Scheduler,

    ) : MvpPresenter<StoreView>() {

    val listCategory = CategoryListPresenter()
    val listProduct = ProductsListPresenter()

    val searchClick = SearchClick()

    val filterClick = FilterClick()

    var  productsFilter = Products()

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

        searchClick.listenerFocusChanged = object : OnFocusChangeListener {
            override fun onFocusChange(p0: View?, p1: Boolean) {
                if (p1) {
                    router.navigateTo(AndroidScreens().search())
                } else {
                    return
                }
            }
        }

        filterClick.itemClickListener = {
            viewState.showBottomDialog()
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
                productsFilter.addAll(products)
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

    fun filter(brand: String?, price: String?, result: String?) {

        val getInts = Scanner(price).useDelimiter("[^\\d]+")
        val firstPrice: Int = getInts.nextInt()
        val secondPrice: Int = getInts.nextInt()

        if (brand != null) {
            if (price != null) {
                if (result != null) {

                    Observable.fromIterable(productsFilter)
                        .filter {
                                      it.title == brand

                                    && it.price >= firstPrice
                                    && it.price <= secondPrice
                                    //&& it.rating.rate.toString() == result
                        }
                        .subscribeOn(Schedulers.io())
                        .observeOn(uiScheduler)
                        .toList()
                        .subscribe({
                            listProduct.products.clear()
                            listProduct.products.addAll(it)
                                   viewState.filter()
                        }, {
                            viewState.onError(it)
                        })
                }
            }
        }
    }
}