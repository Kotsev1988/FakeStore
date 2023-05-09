package com.example.fakestore.di.modules


import com.example.fakestore.di.scope.MainActivityScope
import com.example.fakestore.navigation.AndroidScreens
import com.example.fakestore.navigation.IScreens
import com.github.terrakok.cicerone.Cicerone
import com.github.terrakok.cicerone.Router
import dagger.Module
import dagger.Provides

@Module
class CiceroneModule {
//    private val cicerone: Cicerone<Router> = Cicerone.create()
//
//    @MainActivityScope
//    @Provides
//    fun navigatorHolder() = cicerone.getNavigatorHolder()
//
//    @MainActivityScope
//    @Provides
//    fun router() = cicerone.router

    @MainActivityScope
    @Provides
    fun screens(): IScreens =
        AndroidScreens()
}