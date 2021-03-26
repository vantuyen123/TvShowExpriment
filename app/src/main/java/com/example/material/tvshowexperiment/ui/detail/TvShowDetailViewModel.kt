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
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

enum class TvShowDetailStatus { LOADING, ERROR, DONE }
class TvShowDetailViewModel(
    tvShow: TvShow,
    app: Application,
) : AndroidViewModel(app) {

    private val _selectMovieDetail = MutableLiveData<TvShowDetail>()
    val selectMovieDetail: LiveData<TvShowDetail>
        get() = _selectMovieDetail

    private val _tvShow = MutableLiveData<TvShow>()
    val tvShow: LiveData<TvShow>
        get() = _tvShow


    private val _navigateToHome = MutableLiveData<Boolean?>()
    val navigateToHome: LiveData<Boolean?>
        get() = _navigateToHome

    private val _status = MutableLiveData<TvShowDetailStatus>()
    val status: LiveData<TvShowDetailStatus>
        get() = _status

    private val _showSnackbarEvent = MutableLiveData<Boolean?>()
    val showSnackbarEvent: LiveData<Boolean?>
        get() = _showSnackbarEvent

    init {
        _tvShow.value = tvShow
        getTvShowDetail(tvShow.id.toInt())
    }


    private fun getTvShowDetail(tvShowId: Int) {
        viewModelScope.launch {
            _status.value = TvShowDetailStatus.LOADING
            try {
                val result = TvShowApiService.create().getTvShowDetail(tvShowId).tvShowDetail
                _selectMovieDetail.value = result
                _status.value = TvShowDetailStatus.DONE
            } catch (e: Exception) {
                Log.d("Tag", "${e.message}")
                _status.value = TvShowDetailStatus.ERROR
                _showSnackbarEvent.value = true
            }
        }
    }

    fun onBack() {
        _navigateToHome.value = true
    }

    fun doneNavigating() {
        _navigateToHome.value = null
    }

    fun doneShowingSnackBar() {
        _showSnackbarEvent.value = null
    }
}
