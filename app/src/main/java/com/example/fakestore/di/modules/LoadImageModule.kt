package com.example.fakestore.di.modules

import android.widget.ImageView
import com.example.fakestore.domain.image.ILoadImage
import com.example.fakestore.presentation.loadImage.LoadImage
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class LoadImageModule {

    @Singleton
    @Provides
    fun loadImage() : ILoadImage<ImageView> = LoadImage()
}