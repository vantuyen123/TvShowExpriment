package com.example.material.tvshowexperiment.network

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.example.material.tvshowexperiment.data.TvShow
import retrofit2.HttpException
import java.io.IOException


private const val TVSHOW_STARTING_PAGE_INDEX = 1

class TvShowSearchPagingSource(
    private val service: TvShowApiService,
    private val query: String
) : PagingSource<Int, TvShow>() {

    override fun getRefreshKey(state: PagingState<Int, TvShow>): Int? {
        return state.anchorPosition?.let { anchorPosition ->
            state.closestPageToPosition(anchorPosition)?.prevKey?.plus(1)
                ?: state.closestPageToPosition(anchorPosition)?.nextKey?.minus(1)
        }
    }

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, TvShow> {
        val position = params.key ?: TVSHOW_STARTING_PAGE_INDEX
        return try {
            val response = service.getTvShowSearch(query, position)
            val tvShow = response.tvShows
            val nextKey = if (tvShow.isEmpty()) {
                null
            } else {
                position + (params.loadSize / 20)
            }
            LoadResult.Page(
                tvShow,
                if (position == TVSHOW_STARTING_PAGE_INDEX) null else position - 1,
                nextKey = nextKey
            )
        } catch (e: IOException) {
            LoadResult.Error(e)
        } catch (e: HttpException) {
            LoadResult.Error(e)
        }
    }
}