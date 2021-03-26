package com.example.material.tvshowexperiment.ui.mostpopular

import android.os.Bundle
import android.os.Parcelable
import android.util.Log
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
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

class MostPopularFragment : Fragment() {
    companion object{
        private const val KEY_RECYCLER_STATE = "recycler_state"
    }
    private lateinit var savedRecyclerViewLayoutSate: Parcelable

    private lateinit var viewModel: MostPopularViewModel

    private lateinit var binding: MostPopularFragmentBinding
    private val mostPopularAdapter = MostPopularAdapter(MostPopularAdapter.OnClickListener {
        viewModel.displayTvShowDetail(it)
    })
    private var job: Job? = null

    private fun init() {
        job?.cancel()
        job = lifecycleScope.launch {
            viewModel.getMostPopularTvShow().collectLatest {
                mostPopularAdapter.submitData(it)
            }
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding =
            DataBindingUtil.inflate(inflater, R.layout.most_popular_fragment, container, false)

        viewModel = ViewModelProvider(
            this,
            Injection.providerViewModelFactory()
        ).get(MostPopularViewModel::class.java)

        binding.retryButton.setOnClickListener { mostPopularAdapter.retry() }

        binding.lifecycleOwner = this
        init()
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


    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        Log.d("MostPopularFragment","onSaveInstanceState Called")
        outState.putParcelable(KEY_RECYCLER_STATE,binding.tvShowRecyclerView.layoutManager?.onSaveInstanceState())
    }



    private fun initRecyclerView() {
        binding.tvShowRecyclerView.adapter = mostPopularAdapter.withLoadStateHeaderAndFooter(
            header = TvShowLoadStateAdapter { mostPopularAdapter.retry() },
            footer = TvShowLoadStateAdapter { mostPopularAdapter.retry() }
        )
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
                    "\uD83D\uDE28 Woops ", Toast.LENGTH_SHORT
                ).show()
            }
        }
    }


}