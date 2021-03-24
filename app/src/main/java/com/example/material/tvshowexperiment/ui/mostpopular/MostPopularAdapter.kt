package com.example.material.tvshowexperiment.ui.mostpopular

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.material.tvshowexperiment.databinding.ItemMostPopularBinding
import com.example.material.tvshowexperiment.data.TvShow

class MostPopularAdapter(private val onClickListener: OnClickListener) :
    PagingDataAdapter<TvShow, MostPopularAdapter.MostPopularViewHolder>(DiffCallback()) {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MostPopularViewHolder {
        return MostPopularViewHolder.from(parent)
    }

    override fun onBindViewHolder(holder: MostPopularViewHolder, position: Int) {
        val item = getItem(position)
        holder.bind(item, onClickListener)
    }


    class MostPopularViewHolder(private val binding: ItemMostPopularBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(item: TvShow?, clickListener: OnClickListener) {
            binding.onClick = clickListener
            binding.tvShow = item
            binding.executePendingBindings()
        }

        companion object {
            fun from(parent: ViewGroup): MostPopularViewHolder {
                val layoutInflater = LayoutInflater.from(parent.context)
                val binding = ItemMostPopularBinding.inflate(layoutInflater)
                return MostPopularViewHolder(binding)
            }
        }
    }
    class OnClickListener(val clickListener: (tvShow: TvShow) -> Unit) {
        fun onClick(tvShow: TvShow) = clickListener(tvShow)
    }
}


class DiffCallback : DiffUtil.ItemCallback<TvShow>() {
    override fun areItemsTheSame(oldItem: TvShow, newItem: TvShow): Boolean {
        return oldItem.id == newItem.id
    }

    override fun areContentsTheSame(oldItem: TvShow, newItem: TvShow): Boolean {
        return oldItem == newItem
    }
}





