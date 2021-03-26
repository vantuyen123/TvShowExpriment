package com.example.material.tvshowexperiment.repository

import android.util.Log
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.example.material.tvshowexperiment.data.TvShow
import com.example.material.tvshowexperiment.network.TvShowApiService
import com.example.material.tvshowexperiment.network.TvShowSearchPagingSource
import kotlinx.coroutines.flow.Flow

class TvShowSearchRepository(private val service: TvShowApiService) {

    fun getSearchResultStream(query: String): Flow<PagingData<TvShow>> {
        Log.d("TvShowSearchRepository", "new query:$query")
        return Pager(
            config = PagingConfig(20, enablePlaceholders = false),
            pagingSourceFactory = { TvShowSearchPagingSource(service, query) }
        ).flow
    }
}