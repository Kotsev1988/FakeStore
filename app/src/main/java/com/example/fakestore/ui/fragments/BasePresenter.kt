package com.example.fakestore.ui.fragments

import com.example.fakestore.ui.MainView
import com.example.fakestore.ui.screens.AndroidScreens
import com.github.terrakok.cicerone.Router
import moxy.MvpPresenter

class BasePresenter(private val router: Router): MvpPresenter<BaseView>() {

    override fun onFirstViewAttach() {
        super.onFirstViewAttach()

    }

    fun backClicked(): Boolean{
        router.exit()
        return true
    }

    fun storeNavigate(){
       router.navigateTo(AndroidScreens().store())
    }
}