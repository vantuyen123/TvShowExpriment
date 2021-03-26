package com.example.material.tvshowexperiment.ui.search

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.example.material.tvshowexperiment.data.TvShow
import com.example.material.tvshowexperiment.repository.TvShowSearchRepository
import kotlinx.coroutines.flow.Flow

class SearchViewModel(private val repository: TvShowSearchRepository) : ViewModel() {

    private var currentQueryValue: String? = null

    private var currentSearchResult: Flow<PagingData<TvShow>>? = null

    private val _navigateToTvShowDetail = MutableLiveData<TvShow?>()
    val navigateToTvShowDetail: LiveData<TvShow?>
        get() = _navigateToTvShowDetail

    private val _navigateToHome = MutableLiveData<Boolean?>()
    val navigateToHome: LiveData<Boolean?>
        get() = _navigateToHome

    fun searchTvShow(queryString: String): Flow<PagingData<TvShow>> {
        val lastResult = currentSearchResult
        if (queryString == currentQueryValue && lastResult != null) {
            return lastResult
        }
        currentQueryValue = queryString
        val newResult: Flow<PagingData<TvShow>> = repository.getSearchResultStream(queryString)
            .cachedIn(viewModelScope)
        currentSearchResult = newResult
        return newResult
    }

    fun displayTvShowDetail(tvShow: TvShow) {
        _navigateToTvShowDetail.value = tvShow
    }

    fun displayTvShowDetailComplete() {
        _navigateToTvShowDetail.value = null
    }

    fun navigateToHome() {
        _navigateToHome.value = true
    }

    fun navigateToHomeComplete() {
        _navigateToHome.value = null
    }

}