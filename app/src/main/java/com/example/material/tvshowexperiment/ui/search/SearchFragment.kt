package com.example.material.tvshowexperiment.ui.search

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.KeyEvent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.EditorInfo
import androidx.core.widget.addTextChangedListener
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.paging.LoadState
import com.example.material.tvshowexperiment.Injection
import com.example.material.tvshowexperiment.databinding.SearchFragmentBinding
import com.example.material.tvshowexperiment.ui.mostpopular.MostPopularAdapter
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.distinctUntilChangedBy
import kotlinx.coroutines.flow.filter
import kotlinx.coroutines.launch

class SearchFragment : Fragment() {

    companion object {
        private const val LAST_SEARCH_QUERY: String = "last_search_query"
        private const val DEFAULT_QUERY = "Tv Show"
    }

    private lateinit var binding: SearchFragmentBinding

    private lateinit var viewModel: SearchViewModel

    private val tvShowAdapter = MostPopularAdapter(MostPopularAdapter.OnClickListener {
        viewModel.displayTvShowDetail(it)
    })

    private var searchJob: Job? = null


    private fun search(query: String) {
        searchJob?.cancel()
        searchJob = lifecycleScope.launch {
            viewModel.searchTvShow(query).collectLatest {
                tvShowAdapter.submitData(it)
            }
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = SearchFragmentBinding.inflate(layoutInflater)
        viewModel =
            ViewModelProvider(
                this,
                Injection.providerSearchViewModel()
            ).get(SearchViewModel::class.java)
        binding.lifecycleOwner = this

        viewModel.navigateToHome.observe(viewLifecycleOwner, {
            if (it == true) {
                this.findNavController()
                    .navigate(SearchFragmentDirections.actionSearchFragmentToMostPopularFragment())
                viewModel.navigateToHomeComplete()
            }
        })
        viewModel.navigateToTvShowDetail.observe(viewLifecycleOwner, {
            if (it != null) {
                this.findNavController()
                    .navigate(SearchFragmentDirections.actionSearchFragmentToTvShowDetailFragment(it))
                viewModel.displayTvShowDetailComplete()
            }
        })


        initAdapter()
        val query = savedInstanceState?.getString(LAST_SEARCH_QUERY) ?: DEFAULT_QUERY

        search(query)
        initSearch(query)


        return binding.root

    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        outState.putString(LAST_SEARCH_QUERY, binding.inputSearch.text.trim().toString())
    }

    private fun initAdapter() {
        binding.tvShowRecyclerViewSearch.adapter = tvShowAdapter
    }

    private fun initSearch(query: String) {
        binding.inputSearch.setText(query)

        binding.inputSearch.setOnEditorActionListener { _, actionId, _ ->
            if (actionId == EditorInfo.IME_ACTION_GO) {
                updateTvShowListFormInput()
                true
            } else {
                false
            }
        }

        binding.inputSearch.setOnKeyListener { _, keyCode, event ->
            if (event.action == KeyEvent.ACTION_DOWN && keyCode == KeyEvent.KEYCODE_ENTER) {
                updateTvShowListFormInput()
                true
            } else {
                false
            }
        }

        lifecycleScope.launch {
            tvShowAdapter.loadStateFlow
                .distinctUntilChangedBy { it.refresh }
                .filter { it.refresh is LoadState.NotLoading }
                .collect { binding.tvShowRecyclerViewSearch.scrollToPosition(0) }
        }
    }

    private fun updateTvShowListFormInput() {
        binding.inputSearch.text.trim().let {
            if (it.isNotEmpty()) {
                search(it.toString())
            }
        }
    }
}
