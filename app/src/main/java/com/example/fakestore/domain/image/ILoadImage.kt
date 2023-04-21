package com.example.fakestore.domain.image

interface ILoadImage<T> {
    fun loadImage(url:String, container: T)
    fun loadImage(url:Int, container: T)
}