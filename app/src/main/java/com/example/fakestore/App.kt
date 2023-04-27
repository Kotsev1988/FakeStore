package com.example.fakestore

import android.app.Application
import android.content.Context
import com.example.fakestore.di.AppComponent
import com.example.fakestore.di.DaggerAppComponent
import com.example.fakestore.di.ProductSubComponent
import com.example.fakestore.di.modules.AppModule

class App: Application() {

    companion object{
        lateinit var instance: App
    }

    lateinit var appComponent: AppComponent

    var productSubComponent: ProductSubComponent? = null
        private set


    override fun onCreate() {
        super.onCreate()
        instance = this
        appComponent = DaggerAppComponent.builder()
            .appModule(AppModule(this)).build()
    }

    fun initProductSubcomponent() = appComponent.productSubComponent().also {
        productSubComponent = it
    }

    fun removeProductSubcomponent()  {
        productSubComponent = null
    }
    fun getAppContext(): Context {
        return instance.applicationContext
    }
}