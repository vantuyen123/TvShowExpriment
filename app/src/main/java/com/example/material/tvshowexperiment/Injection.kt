package com.example.material.tvshowexperiment

import androidx.lifecycle.ViewModelProvider
import com.example.material.tvshowexperiment.network.TvShowApiService
import com.example.material.tvshowexperiment.repository.MostPopularRepository
import com.example.material.tvshowexperiment.repository.TvShowSearchRepository
import com.example.material.tvshowexperiment.ui.mostpopular.MostPopularViewModelFactory
import com.example.material.tvshowexperiment.ui.search.SearchViewModelFactory

object Injection {

    private fun provideTvShowRepository(): MostPopularRepository {
        return MostPopularRepository(TvShowApiService.create())
    }


    fun providerViewModelFactory():ViewModelProvider.Factory{
        return MostPopularViewModelFactory(provideTvShowRepository())
    }

    private fun providerTvShowSearchRepository():TvShowSearchRepository{
        return TvShowSearchRepository(TvShowApiService.create())
    }

    fun providerSearchViewModel():ViewModelProvider.Factory{
        return SearchViewModelFactory(providerTvShowSearchRepository())
    }
}