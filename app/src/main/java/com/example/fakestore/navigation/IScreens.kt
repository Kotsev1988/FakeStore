package com.example.fakestore.navigation

import com.github.terrakok.cicerone.Screen

interface IScreens {
    fun store(): Screen
    fun product(id: String): Screen
    fun search(): Screen
}