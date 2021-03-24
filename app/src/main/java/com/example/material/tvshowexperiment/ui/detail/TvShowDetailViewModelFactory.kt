package com.example.material.tvshowexperiment.ui.detail

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.material.tvshowexperiment.data.TvShow

@Suppress("UNCHECKED_CAST")
class TvShowDetailViewModelFactory(
    private val tvShow: TvShow,
    private val application: Application
) : ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(TvShowDetailViewModel::class.java)) {
            return TvShowDetailViewModel(tvShow, application) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}