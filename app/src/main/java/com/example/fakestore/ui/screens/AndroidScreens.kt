package com.example.fakestore.ui.screens

import com.example.fakestore.ui.fragments.BaseFragment
import com.example.fakestore.ui.fragments.ProductFragment
import com.example.fakestore.ui.fragments.StoreFragment
import com.github.terrakok.cicerone.Screen
import com.github.terrakok.cicerone.androidx.FragmentScreen

class AndroidScreens: IScreens {
    override fun baseFragment(): Screen = FragmentScreen{
        BaseFragment.newInstance()
    }


    override fun store(): Screen = FragmentScreen {
        StoreFragment.newInstance()
    }

//    override fun myCart(): Screen = FragmentScreen {
//        MyCartFragmnet.newInstance()
//    }
//
//    override fun likes(): Screen = FragmentScreen {
//        LikesFragmnet.newInstance()
//    }
//
//    override fun myProfile(): Screen = FragmentScreen {
//        MyProfileFragmnet.newInstance()
//    }

    override fun product(): Screen = FragmentScreen {
        ProductFragment.newInstance()
    }
}