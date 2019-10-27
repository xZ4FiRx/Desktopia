package com.example.z4fir.desktopia.screens.showcase.instagram.adapter

import android.widget.ImageView
import androidx.core.net.toUri
import androidx.databinding.BindingAdapter
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.request.RequestOptions
import com.example.z4fir.desktopia.R


@BindingAdapter("imageUrl")
fun bindImage(imgView: ImageView, imgUrl: String?) {

    imgUrl?.let {
        val imgUri = imgUrl.toUri().buildUpon().scheme("https").build()
        Glide.with(imgView.context)
            .load(imgUri)
            .diskCacheStrategy(DiskCacheStrategy.ALL)
            .apply(RequestOptions()
                .error(R.drawable.ic_broken_image))
            .into(imgView)
    }
}

@BindingAdapter("imageUrlThumbnail")
fun bindImageThumbnail(imgView: ImageView, imgUrl: String?) {

    imgUrl?.let {
        val imgUri = imgUrl.toUri().buildUpon().scheme("https").build()
        Glide.with(imgView.context)
            .load(imgUri)
            .centerCrop()
            .thumbnail(0.1f)
            .diskCacheStrategy(DiskCacheStrategy.ALL)
            .apply(RequestOptions()
                .placeholder(R.drawable.loading_animation)
                .error(R.drawable.ic_broken_image))
            .into(imgView)
    }
}