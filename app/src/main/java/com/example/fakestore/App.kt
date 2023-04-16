package com.example.fakestore

import android.app.Application
import com.example.fakestore.di.AppComponent
import com.example.fakestore.di.DaggerAppComponent
import com.example.fakestore.di.modules.AppModule

class App: Application() {

    companion object{
        lateinit var instance: App
    }
    lateinit var appComponent: AppComponent

    override fun onCreate() {
        super.onCreate()
        instance = this
        appComponent = DaggerAppComponent.builder()
            .appModule(AppModule(this)).build()
    }
}