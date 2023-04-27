package com.example.fakestore.navigation

import com.example.fakestore.presentation.fragments.MyCartFragment
import com.example.fakestore.presentation.fragments.ProductFragment
import com.example.fakestore.presentation.fragments.SearchingFragment
import com.example.fakestore.presentation.fragments.StoreFragment
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