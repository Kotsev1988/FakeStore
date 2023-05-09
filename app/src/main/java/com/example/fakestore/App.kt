package com.example.fakestore

import android.app.Application
import android.content.Context
import com.example.fakestore.di.*
import com.example.fakestore.di.AppModule
import com.example.fakestore.utils.InjectUtils


class App: Application(), BaseComponentProvider {

    companion object{
        lateinit var instance: App
    }


   private lateinit var baseComponent: BaseComponent


    lateinit var appComponent: AppComponent



    var productSubComponent: ProductSubComponent? = null
        private set


    override fun onCreate() {
        super.onCreate()
        instance = this



        baseComponent =  DaggerBaseComponent
            .builder()
            .appModule(AppModule(this))
            .build()
        baseComponent.inject(this)

        appComponent = DaggerAppComponent
            .builder()
            .baseComponent(InjectUtils.provideBaseComponent(this))
            .appModule(AppModule(this))
            .build()


    }

//    fun initProductSubcomponent() = baseComponent.productSubComponent().also {
//        productSubComponent = it
//    }
//
//    fun removeProductSubcomponent()  {
//        productSubComponent = null
//    }
    fun getAppContext(): Context {
        return instance.applicationContext
    }

    override fun provideBaseComponent(): BaseComponent = baseComponent

}