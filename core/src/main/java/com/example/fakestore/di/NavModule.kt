package com.example.fakestore.di


import com.example.fakestore.navigation.IScreens
import com.github.terrakok.cicerone.Cicerone
import com.github.terrakok.cicerone.Router
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class NavModule {

    val cicerone: Cicerone<Router> = Cicerone.create()

    @Singleton
    @Provides
    fun navigatorHolder() = cicerone.getNavigatorHolder()

    @Singleton
    @Provides
    fun router(): Router = cicerone.router

//    @Singleton
//    @Provides
//    fun screens(): IScreens =
//        AndroidScreens()
}