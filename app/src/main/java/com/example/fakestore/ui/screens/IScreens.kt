package com.example.fakestore.ui.screens

import com.github.terrakok.cicerone.Screen

interface IScreens {
    fun baseFragment(): Screen
    fun store(): Screen
//    fun myCart(): Screen
//    fun likes(): Screen
//    fun myProfile(): Screen
    fun product():Screen
}