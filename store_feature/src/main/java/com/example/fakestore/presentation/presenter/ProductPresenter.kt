package com.example.fakestore.presentation.presenter

import com.example.fakestore.domain.IGetProductById
import com.example.fakestore.presentation.view.ProductView
import com.example.fakestore.productsEntity.ProductsItem
import com.github.terrakok.cicerone.Router
import io.reactivex.rxjava3.core.Scheduler
import moxy.MvpPresenter

class ProductPresenter(
//    private val id: String?,
//    private val router: Router,
//    private val uiScheduler: Scheduler,
//    private val product: IGetProductById,
   // private val myCard: IMyCardProducts,
) : MvpPresenter<ProductView>() {

//    lateinit var productsItem: ProductsItem
//
//
//    override fun onFirstViewAttach() {
//        super.onFirstViewAttach()
//        loadData()
//        isContain()
//    }
//
//    private fun isContain() {
////        if (id != null) {
////            myCard.isContain(id).observeOn(uiScheduler).subscribe({
////                if (it) {
////
////                    viewState.productInMyCard()
////                } else {
////                    viewState.turnOnAddButton()
////                }
////
////            },
////                {
////
////                })
////        }
//    }
//
//    private fun loadData() {
//
//        if (id != null) {
//            product.getProductById(id).observeOn(uiScheduler)
//                .subscribe({ product ->
//                    productsItem = product
//                    viewState.setTitle(product.title)
//                    viewState.setDescription(product.description)
//                    viewState.setPrice((product.price).toString())
//                    viewState.setImage(product.image)
//                },
//
//                    {
//                        viewState.onError(it)
//                    })
//        }
//    }
//
//
//    fun backClicked(): Boolean {
//
//        return true
//    }
//
//    fun addToCard() {
////        productsItem?.let {
////            myCard.insertProductToMyCard(productsItem).observeOn(uiScheduler)
////                .subscribe({
////
////                    viewState.addedToMyCard()
////                }, {
////                    viewState.onError(Throwable(it.message))
////                })
////        }
//
//    }

}