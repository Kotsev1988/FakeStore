package com.example.fakestore.ui.loadImage

import android.widget.ImageView
import com.bumptech.glide.Glide

class LoadImage: ILoadImage<ImageView> {
    override fun loadImage(url: String, container: ImageView) {
        Glide.with(container.context)
            .load(url)
            .into(container)
    }

    override fun loadImage(url: Int, container: ImageView) {
        Glide.with(container.context)
            .load(url)
            .into(container)
    }

}