package com.example.fakestore.presenter

import com.example.fakestore.navigation.IScreens
import com.example.fakestore.domain.view.MainView
import com.github.terrakok.cicerone.Router
import moxy.MvpPresenter

class MainPresenter(private val router: Router, private val screens: IScreens): MvpPresenter<MainView>() {

    override fun onFirstViewAttach() {
        super.onFirstViewAttach()
        router.replaceScreen(screens.store())
    }

    fun backClicked(){
        router.exit()
    }


}