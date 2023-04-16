package com.example.fakestore.di.modules

import com.example.fakestore.App
import dagger.Module
import dagger.Provides
@Module
class AppModule(val app: App) {
    @Provides
    fun app() : App = app
}