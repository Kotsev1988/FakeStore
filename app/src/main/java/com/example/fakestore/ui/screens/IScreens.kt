package com.example.fakestore.ui.screens

import com.github.terrakok.cicerone.Screen

interface IScreens {
    fun store(): Screen
    fun product(id: String): Screen
    fun search(): Screen
}