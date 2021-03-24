package com.example.material.tvshowexperiment.network

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.example.material.tvshowexperiment.data.TvShow
import retrofit2.HttpException
import java.io.IOException

private const val TVSHOW_STARTING_PAGE_INDEX = 1

class TvShowPagingSource(
    private val service: TvShowApiService,
) : PagingSource<Int, TvShow>() {
    override fun getRefreshKey(state: PagingState<Int, TvShow>): Int? {
        TODO("Not yet implemented")
    }

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, TvShow> {
        val position = params.key ?: TVSHOW_STARTING_PAGE_INDEX
        return try {
            val response = service.getMostPopular(position)
            val tvShows = response.tvShows
            val nextKey = if (tvShows.isEmpty()) {
                null
            } else {
                position + (params.loadSize/20)
            }
            LoadResult.Page(
                data = tvShows,
                prevKey = if (position == TVSHOW_STARTING_PAGE_INDEX) null else position - 1,
                nextKey = nextKey
            )
        } catch (e: IOException) {
            return LoadResult.Error(e)
        } catch (e: HttpException) {
            return LoadResult.Error(e)
        }
    }
}