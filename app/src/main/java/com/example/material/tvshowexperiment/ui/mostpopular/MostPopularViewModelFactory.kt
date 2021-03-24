package com.example.material.tvshowexperiment.ui.mostpopular

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.material.tvshowexperiment.repository.MostPopularRepository
import java.lang.IllegalArgumentException

@Suppress("UNCHECKED_CAST")
class MostPopularViewModelFactory(private val repository: MostPopularRepository) :
    ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(MostPopularViewModel::class.java)) {
            return MostPopularViewModel(repository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}