package com.example.fakestore.presenter

import com.example.fakestore.domain.IMyCardProducts
import com.example.fakestore.domain.productsEntity.ProductsItem
import com.example.fakestore.domain.view.MyCartView
import com.github.terrakok.cicerone.Router
import io.reactivex.rxjava3.core.Scheduler
import moxy.MvpPresenter

class MyCartPresenter(
    private val router: Router,
    private val uiScheduler: Scheduler,
    private val myCard: IMyCardProducts
): MvpPresenter<MyCartView>()  {

    val myCartListProducts = MyCardProductsPresenter()
    private val prices : HashMap<Int, Int> = hashMapOf()


    override fun onFirstViewAttach() {
        super.onFirstViewAttach()

        init()
        myCartListProducts.itemClickListenerIncrease = {

            val key = myCartListProducts.myProducts[it.pos].id
            val defaultValue = myCartListProducts.myProducts[it.pos].count

            prices[key] = prices.getOrDefault(key, defaultValue)+1
            prices[key]?.let { it1 ->
                myCard.update(key.toString(), it1).observeOn(uiScheduler).subscribe({
                    myCard.getAllMyCard()
                        .observeOn(uiScheduler)
                        .subscribe({
                            myCartListProducts.myProducts.clear()
                            myCartListProducts.myProducts.addAll(it)
                            viewState.updatePrices()
                            totalPrice(it)
                        },
                            {


                            })
                },{

                })
            }

        }

        myCartListProducts.itemClickListenerDecrease = {
            val key = myCartListProducts.myProducts[it.pos].id
            val defaultValue = myCartListProducts.myProducts[it.pos].count
            prices[key] = prices.getOrDefault(key, defaultValue)-1

            prices[key]?.let { it1 ->
                myCard.update(key.toString(), it1).observeOn(uiScheduler).subscribe({
                    myCard.getAllMyCard()
                        .observeOn(uiScheduler)
                        .subscribe({
                            myCartListProducts.myProducts.clear()
                            myCartListProducts.myProducts.addAll(it)
                            viewState.updatePrices()
                            totalPrice(it)
                        },
                            {


                            })
                },{

                })
            }
        }

        myCartListProducts.itemClickListenerDelete = {
            val product = myCartListProducts.myProducts[it.pos]
            myCard.delete(product).observeOn(uiScheduler)
                .subscribe({
                    myCartListProducts.myProducts.remove(product)
                    viewState.updatePrices()
                    totalPrice(myCartListProducts.myProducts)
                },{

                })

        }
    }

    fun init(){
        myCard.getAllMyCard()
            .observeOn(uiScheduler)
            .subscribe({
                myCartListProducts.myProducts.clear()
                myCartListProducts.myProducts.addAll(it)
                viewState.updateList()
                totalPrice(it)
            },
                {


                })
    }

    private fun totalPrice(myProducts: List<ProductsItem>){
        var totalPrice = 0
        myProducts.forEach {
            totalPrice += it.price.toInt()*it.count
        }

        viewState.setTotalPrice(totalPrice.toString())
    }

    fun backClicked(): Boolean {
        router.exit()
        return true
    }
}