package com.example.material.tvshowexperiment

import android.view.View
import android.widget.ImageView
import android.widget.ProgressBar
import androidx.databinding.BindingAdapter
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.example.material.tvshowexperiment.ui.detail.TvShowDetailStatus

//@GlideModule
//class TvShowGlideOptions : AppGlideModule() {

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

//}

@BindingAdapter("status")
fun bindStatus(statusProgressBar: ProgressBar, status: TvShowDetailStatus) {
    when (status) {
        TvShowDetailStatus.LOADING -> {
            statusProgressBar.visibility = View.VISIBLE
        }
        TvShowDetailStatus.DONE -> {
            statusProgressBar.visibility = View.GONE
        }
        else -> TvShowDetailStatus.ERROR
    }
}

