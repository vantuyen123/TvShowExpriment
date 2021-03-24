package com.example.material.tvshowexperiment.ui.mostpopular

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.view.isVisible
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.paging.LoadState
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.material.tvshowexperiment.Injection
import com.example.material.tvshowexperiment.R
import com.example.material.tvshowexperiment.databinding.MostPopularFragmentBinding
import com.example.material.tvshowexperiment.ui.TvShowLoadStateAdapter
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

class MostPopularFragment : Fragment() {


    private lateinit var viewModel: MostPopularViewModel
    private lateinit var binding: MostPopularFragmentBinding
    private val mostPopularAdapter = MostPopularAdapter(MostPopularAdapter.OnClickListener {
        viewModel.displayTvShowDetail(it)
    })

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding =
            DataBindingUtil.inflate(
                inflater,
                R.layout.most_popular_fragment,
                container,
                false
            )


        viewModel = ViewModelProvider(
            this,
            Injection.providerViewModelFactory()
        ).get(MostPopularViewModel::class.java)
        binding.retryButton.setOnClickListener { mostPopularAdapter.retry() }

        binding.lifecycleOwner = this
        initRecyclerView()

        viewModel.navigateToTvShowDetail.observe(viewLifecycleOwner, {
            if (it != null) {
                this.findNavController().navigate(
                    MostPopularFragmentDirections.actionMostPopularFragmentToTvShowDetailFragment(it)
                )
                viewModel.displayTvShowDetailComplete()
            }
        })


        return binding.root

    }

    private fun initRecyclerView() {
        binding.tvShowRecyclerView.adapter = mostPopularAdapter.withLoadStateHeaderAndFooter(
            header = TvShowLoadStateAdapter { mostPopularAdapter.retry() },
            footer = TvShowLoadStateAdapter { mostPopularAdapter.retry() }
        )

        lifecycleScope.launch {
            viewModel.newResult.collectLatest {
                mostPopularAdapter.submitData(it)
            }
        }

        binding.tvShowRecyclerView.apply {
            layoutManager = LinearLayoutManager(context)
            setHasFixedSize(true)
        }
        mostPopularAdapter.addLoadStateListener { loadState ->
            binding.tvShowRecyclerView.isVisible = loadState.source.refresh is LoadState.NotLoading
            binding.progressBar.isVisible = loadState.source.refresh is LoadState.Loading
            binding.retryButton.isVisible = loadState.source.refresh is LoadState.Error

            val errorState = loadState.source.append as? LoadState.Error
                ?: loadState.source.prepend as? LoadState.Error
                ?: loadState.append as? LoadState.Error
                ?: loadState.prepend as? LoadState.Error

            errorState?.let {
                Toast.makeText(
                    context,
                    "\uD83D\uDE28 Woops ${it.error}", Toast.LENGTH_SHORT
                ).show()
            }
        }
    }


}