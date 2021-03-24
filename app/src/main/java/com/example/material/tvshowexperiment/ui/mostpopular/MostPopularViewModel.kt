package com.example.material.tvshowexperiment.ui.mostpopular

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.paging.PagingData
import com.example.material.tvshowexperiment.data.TvShow
import com.example.material.tvshowexperiment.repository.MostPopularRepository
import kotlinx.coroutines.flow.Flow

class MostPopularViewModel(repository: MostPopularRepository) : ViewModel() {


    val newResult: Flow<PagingData<TvShow>> = repository.getPopularMovie()

    private val _navigateToTvShowDetail = MutableLiveData<TvShow?>()
    val navigateToTvShowDetail: LiveData<TvShow?>
        get() = _navigateToTvShowDetail

    fun displayTvShowDetail(tvShow: TvShow) {
        _navigateToTvShowDetail.value = tvShow
    }

    fun displayTvShowDetailComplete() {
        _navigateToTvShowDetail.value = null
    }
}