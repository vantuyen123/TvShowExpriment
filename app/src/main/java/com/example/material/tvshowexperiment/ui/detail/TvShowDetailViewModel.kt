package com.example.material.tvshowexperiment.ui.detail

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.material.tvshowexperiment.data.TvShow
import com.example.material.tvshowexperiment.data.TvShowDetail
import com.example.material.tvshowexperiment.network.TvShowApiService
import com.example.material.tvshowexperiment.network.TvShowDetailResponse
import kotlinx.coroutines.launch

class TvShowDetailViewModel(
    tvShow: TvShow,
    app: Application,
) : AndroidViewModel(app) {

    private val _selectMovieDetail = MutableLiveData<TvShowDetail>()
    val selectMovieDetail: LiveData<TvShowDetail>
        get() = _selectMovieDetail


    fun getTvShowDetail(tvShowId: Int) {
        viewModelScope.launch {
            try {
                 val result = TvShowApiService.create().getTvShowDetail(tvShowId).tvShowDetail
              _selectMovieDetail.value = result
                Log.d("Tag","${selectMovieDetail.value}")
            } catch (e: Exception) {
                Log.d("Tag", "${e.message}")
            }
        }
    }
}
