package com.example.fakestore.ui.screens

import com.example.fakestore.ui.fragments.searchingFragment.SearchingFragment
import com.example.fakestore.ui.fragments.productFragment.ProductFragment
import com.example.fakestore.ui.fragments.storeFragment.StoreFragment
import com.github.terrakok.cicerone.Screen
import com.github.terrakok.cicerone.androidx.FragmentScreen

class AndroidScreens: IScreens {



    override fun store(): Screen = FragmentScreen {
        StoreFragment.newInstance()
    }

    override fun product(id: String): Screen = FragmentScreen {
        ProductFragment.newInstance(id)
    }

    override fun search(): Screen  = FragmentScreen{
        SearchingFragment.newInstance()
    }


}