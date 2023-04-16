package com.example.fakestore.di.modules

import com.example.fakestore.ui.screens.AndroidScreens
import com.example.fakestore.ui.screens.IScreens
import com.github.terrakok.cicerone.Cicerone
import com.github.terrakok.cicerone.Router
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class CiceroneModule {
    val cicerone: Cicerone<Router> = Cicerone.create()

    @Singleton
    @Provides
    fun navigatorHolder() = cicerone.getNavigatorHolder()

    @Singleton
    @Provides
    fun router() = cicerone.router

    @Singleton
    @Provides
    fun screens(): IScreens = AndroidScreens()
}