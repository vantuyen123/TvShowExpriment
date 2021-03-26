package com.example.material.tvshowexperiment.repository

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.example.material.tvshowexperiment.data.TvShow
import com.example.material.tvshowexperiment.network.TvShowApiService
import com.example.material.tvshowexperiment.network.TvShowPagingSource
import com.example.material.tvshowexperiment.network.TvShowResponse
import kotlinx.coroutines.flow.Flow

class MostPopularRepository(private val service: TvShowApiService) {
    fun getPopularMovie(): Flow<PagingData<TvShow>> {
        return Pager(
            config = PagingConfig(
                pageSize = NETWORK_SIZE,
                enablePlaceholders = false
            ), pagingSourceFactory = { TvShowPagingSource(service) }
        ).flow
    }


    companion object {

        const val NETWORK_SIZE = 20
    }

}