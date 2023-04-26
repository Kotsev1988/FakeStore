package com.example.fakestore.navigation

import com.example.fakestore.ui.fragments.MyCartFragment
import com.example.fakestore.ui.fragments.ProductFragment
import com.example.fakestore.ui.fragments.SearchingFragment
import com.example.fakestore.ui.fragments.StoreFragment
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

    override fun myCart(): Screen = FragmentScreen{
        MyCartFragment.newInstance()
    }


}