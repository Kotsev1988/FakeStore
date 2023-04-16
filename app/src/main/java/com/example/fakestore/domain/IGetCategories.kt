package com.example.fakestore.domain

import io.reactivex.rxjava3.core.Single

interface IGetCategories {
    fun getCategories(): Single<List<String>>

}