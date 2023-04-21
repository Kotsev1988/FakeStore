package com.example.fakestore.presenter

import com.example.fakestore.domain.IGetProductById
import com.example.fakestore.domain.view.ProductView
import com.github.terrakok.cicerone.Router
import io.reactivex.rxjava3.core.Scheduler
import moxy.MvpPresenter

class ProductPresenter(
    private val id: String?,
    private val router: Router,
    private val uiScheduler: Scheduler,
    private val product: IGetProductById,
) : MvpPresenter<ProductView>() {

    override fun onFirstViewAttach() {
        super.onFirstViewAttach()
        loadData()
    }

    private fun loadData() {

        if (id != null) {
            product.getProductById(id).observeOn(uiScheduler)
                .subscribe({ product ->
                    viewState.setTitle(product.title)
                    viewState.setDescription(product.description)
                    viewState.setPrice((product.price).toString())
                    viewState.setImage(product.image)
                },

                    {
                        viewState.onError(it)
                    })
        }
    }


    fun backClicked(): Boolean {
        router.exit()
        return true
    }

}