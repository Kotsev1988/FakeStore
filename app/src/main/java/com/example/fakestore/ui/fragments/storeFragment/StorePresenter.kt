package com.example.fakestore.ui.fragments.storeFragment

import androidx.appcompat.widget.SearchView.OnCloseListener
import androidx.appcompat.widget.SearchView.OnQueryTextListener
import com.example.fakestore.domain.IGetAllProducts
import com.example.fakestore.domain.IGetCategories
import com.example.fakestore.domain.IGetProducts
import com.example.fakestore.domain.productsEntity.ProductsItem
import com.example.fakestore.ui.delegateAdapter.bestSellers.ProductsListPresenter
import com.example.fakestore.ui.delegateAdapter.categories.CategoryListPresenter
import com.example.fakestore.ui.delegateAdapter.search.SearchClick
import com.example.fakestore.ui.delegateAdapter.search.SearchListResultPresenter
import com.example.fakestore.ui.screens.AndroidScreens
import com.github.terrakok.cicerone.Router
import io.reactivex.rxjava3.core.Completable
import io.reactivex.rxjava3.core.Observable
import io.reactivex.rxjava3.core.Scheduler
import moxy.MvpPresenter
import java.util.concurrent.TimeUnit

class StorePresenter(
    private val categoryList: IGetCategories,
    private val productList: IGetProducts,
    private val searchingData: IGetAllProducts,
    private val router: Router,
    private val uiScheduler: Scheduler,

    ) : MvpPresenter<StoreView>() {

    val listCategory = CategoryListPresenter()
    val listProduct = ProductsListPresenter()
    val searchingPresenter = SearchListResultPresenter()

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

        val listS = arrayListOf<ProductsItem>()



        searchingPresenter.listener = object : OnQueryTextListener {

            override fun onQueryTextSubmit(query: String?): Boolean {
                println("query search")
                return false
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                viewState.updateListOnSearching()
                if (newText != null) {
                    if (newText.equals("")) {
                        searchingPresenter.results.clear()
                        viewState.updateListOnSearching()

                    }

                    else

                    {

                        listS.clear()

                        searchingData.getAllProducts(newText)
                            .toObservable()
                            .debounce(300, TimeUnit.MILLISECONDS)
                            .distinct()
                            .switchMap {
                                Observable.fromIterable(it)
                            }.filter {
                                it.title.contains(newText)
                            }
                            .switchMap { it ->
                                Observable.just(it).map { mapping ->
                                    println("RESS " + mapping.title)
                                    listS.add(mapping)
                                    listS
                                }
                            }
                            .observeOn(uiScheduler).subscribe(
                                { result ->
                                    searchingPresenter.results.clear()
                                    searchingPresenter.results.addAll(result)

                                    viewState.updateSearchingList()
                                },
                                {
                                    viewState.onError(it)
                                }
                            )
                    }
                }
                return true
            }

        }
        searchingPresenter.listenerClose = OnCloseListener {
            searchingPresenter.results.clear()

            viewState.init()
            loadCategories()
            false
        }

        searchingPresenter.itemClickListener = {
            val id = searchingPresenter.results[it.pos].id
            router.navigateTo(AndroidScreens().product(id.toString()))
            println("IT " + searchingPresenter.results[it.pos])
        }

        searchClick.itemClickListener = {
            println("Click on SearchView")
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