package com.example.material.tvshowexperiment.ui

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.paging.LoadState
import androidx.paging.LoadStateAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.material.tvshowexperiment.R
import com.example.material.tvshowexperiment.data.TvShow
import com.example.material.tvshowexperiment.databinding.TvShowLoadStateFooterViewItemBinding

class TvShowLoadStateAdapter(
    private val retry: () -> Unit,
) :
    LoadStateAdapter<TvShowLoadStateAdapter.TvShowLoadStateViewHolder>() {

    override fun onBindViewHolder(holder: TvShowLoadStateViewHolder, loadState: LoadState) {
        holder.bind(loadState)
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        loadState: LoadState
    ): TvShowLoadStateViewHolder {
        return TvShowLoadStateViewHolder.create(parent, retry)
    }

    class TvShowLoadStateViewHolder(
        private val binding: TvShowLoadStateFooterViewItemBinding,
        retry: () -> Unit
    ) : RecyclerView.ViewHolder(binding.root) {
        init {
            binding.retryButton.setOnClickListener { retry.invoke() }
        }

        fun bind(loadState: LoadState) {
            if (loadState is LoadState.Error) {
                binding.errorMsg.text = loadState.error.localizedMessage
            }
            binding.progressBar.isVisible = loadState is LoadState.Loading
            binding.retryButton.isVisible = loadState !is LoadState.Loading
            binding.retryButton.isVisible = loadState !is LoadState.Loading
        }

        companion object {
            fun create(parent: ViewGroup, retry: () -> Unit): TvShowLoadStateViewHolder {
                val view = LayoutInflater.from(parent.context)
                    .inflate(R.layout.tv_show_load_state_footer_view_item, parent, false)
                val binding = TvShowLoadStateFooterViewItemBinding.bind(view)
                return TvShowLoadStateViewHolder(binding, retry)
            }
        }
    }
}

