package com.example.fakestore.ui.fragments.searchingFragment

import androidx.appcompat.widget.SearchView
import com.example.fakestore.domain.IGetAllProducts
import com.example.fakestore.ui.delegateAdapter.bestSellers.ProductsListPresenter
import com.example.fakestore.ui.fragments.searchingFragment.search.SearchListResultPresenter
import com.example.fakestore.ui.screens.AndroidScreens
import com.github.terrakok.cicerone.Router
import io.reactivex.rxjava3.core.Observable
import io.reactivex.rxjava3.core.Scheduler
import moxy.MvpPresenter
import java.util.concurrent.TimeUnit

class SearchingPresenter(private val searchingData: IGetAllProducts,
                         private val router: Router,
                         private val uiScheduler: Scheduler): MvpPresenter<SearchingView>() {



    val searchingPresenter = SearchListResultPresenter()
    val listProduct = ProductsListPresenter()

    override fun onFirstViewAttach() {
        super.onFirstViewAttach()

        viewState.init()


    searchingPresenter.listener =
    object : SearchView.OnQueryTextListener {

        override fun onQueryTextSubmit(query: String?): Boolean {

            return false
        }

        override fun onQueryTextChange(newText: String?): Boolean {

            if (newText != null) {
                viewState.updateListOnSearching()
                if (!newText.isNullOrBlank()){

                searchingData.getAllProducts(newText)
                    .toObservable()
                    .debounce(300, TimeUnit.MILLISECONDS)
                    .distinct()
                    .switchMap { it ->
                        Observable.fromIterable(it)
                            .filter { item ->
                                item.title.contains(newText)
                            }
                            .toList()
                            .toObservable()
                    }
                    .observeOn(uiScheduler)
                    .subscribe(
                        { result ->
                            searchingPresenter.results.clear()
                            searchingPresenter.results.addAll(result)
                            viewState.updateSearchingList()

                        },
                        {
                            viewState.onError(it)

                        }
                    )
            }else{
                    searchingPresenter.results.clear()
                    viewState.updateListOnSearching()
                }

            } else{
                searchingPresenter.results.clear()
                viewState.updateSearchingList()
            }
            return false
        }
    }

    searchingPresenter.listenerClose = SearchView.OnCloseListener{

        searchingPresenter.results.clear()
        false
    }

    searchingPresenter.itemClickListener =
    {
        val id = searchingPresenter.results[it.pos].id

        router.navigateTo(AndroidScreens().product(id.toString()))
    }
}

    fun backClicked(): Boolean {
        router.exit()
        return true
    }
}