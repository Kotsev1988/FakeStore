package com.example.fakestore.presentation.presenter

import com.example.fakestore.presentation.view.MainView
import moxy.MvpPresenter

class MainPresenter(
   // private val router: Router,
   // private val screens: com.example.fakestore.navigation.IScreens
    ): MvpPresenter<MainView>() {

    override fun onFirstViewAttach() {
        super.onFirstViewAttach()
       // router.replaceScreen(screens.store())
    }

    fun backClicked(){

      //  router.exit()
    }

    fun storeNavigate(){
        //router.navigateTo(screens.store())
    }

    fun myCartNavigate(){
       // router.navigateTo(screens.myCart())
    }


}