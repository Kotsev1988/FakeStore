package com.example.fakestore.ui.loadImage

interface ILoadImage<T> {
    fun loadImage(url:String, container: T)
    fun loadImage(url:Int, container: T)
}