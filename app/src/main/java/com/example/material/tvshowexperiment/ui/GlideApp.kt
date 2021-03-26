package com.example.material.tvshowexperiment.ui

import android.content.Context
import android.widget.ImageView
import androidx.databinding.BindingAdapter
import com.bumptech.glide.GlideBuilder
import com.bumptech.glide.annotation.GlideModule
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.module.AppGlideModule
import com.bumptech.glide.request.RequestOptions
import com.bumptech.glide.signature.ObjectKey
import com.example.material.tvshowexperiment.R

//@GlideModule
//class AppGlideOptions : AppGlideModule() {
//   @BindingAdapter("imageLink")
//   fun loadImage(imgView:ImageView,thumbnailPath: String?){
//       GlideApp.with(imgView.context)
//       .load(thumbnailPath)
//           .apply(
//               RequestOptions()
//                   .placeholder(R.drawable.loading_animation)
//                   .error(R.drawable.ic_broken_image)
//           )
//           .into(imgView)
//   }
//}