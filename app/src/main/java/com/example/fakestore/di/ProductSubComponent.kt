package com.example.fakestore.di


import com.example.fakestore.di.product.ProductScope
import com.example.fakestore.di.product.module.ProductModule
import com.example.fakestore.ui.fragments.ProductFragment
import dagger.Subcomponent

@ProductScope
@Subcomponent(
    modules = [
        ProductModule::class
    ]
)
interface ProductSubComponent {
    fun inject(productFragment: ProductFragment)
}