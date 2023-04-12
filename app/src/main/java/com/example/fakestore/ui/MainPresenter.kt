package com.example.fakestore.ui

import com.example.fakestore.ui.screens.IScreens
import com.github.terrakok.cicerone.Router
import moxy.MvpPresenter

class MainPresenter(private val router: Router, private val screens: IScreens): MvpPresenter<MainView>() {

    override fun onFirstViewAttach() {
        super.onFirstViewAttach()
        router.replaceScreen(screens.baseFragment())
    }

    fun backClicked(){
        router.exit()
    }

}