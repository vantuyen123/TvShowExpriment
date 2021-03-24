package com.example.material.tvshowexperiment

import android.widget.ImageView
import androidx.databinding.BindingAdapter
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions

@BindingAdapter("imgUrlLink")
fun bindImage(imgView: ImageView, thumbnailPath: String?) {
    thumbnailPath?.let {
        Glide.with(imgView.context)
            .load(thumbnailPath)
            .apply(
                RequestOptions()
                    .placeholder(R.drawable.loading_animation)
                    .error(R.drawable.ic_broken_image)
            )
            .into(imgView)
    }
}