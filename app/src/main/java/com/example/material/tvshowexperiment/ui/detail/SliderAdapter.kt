package com.example.material.tvshowexperiment.ui.detail

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.material.tvshowexperiment.databinding.ItemImageSliderBinding

class SliderAdapter() :
    RecyclerView.Adapter<SliderAdapter.SliderViewHolder>() {

    var data = listOf<String>()
    set(value){
        field = value
        notifyDataSetChanged()
    }
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SliderViewHolder {
        return SliderViewHolder.form(parent)
    }


    override fun onBindViewHolder(holder: SliderViewHolder, position: Int) {
        val items = data[position]
        holder.bind(items)
    }


    override fun getItemCount(): Int = data.size

    class SliderViewHolder(val binding: ItemImageSliderBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(items: String) {
            binding.tvShowDetailPictures = items
            binding.executePendingBindings()
        }

        companion object {
            fun form(parent: ViewGroup): SliderViewHolder {
                val layoutInflater = LayoutInflater.from(parent.context)
                val binding = ItemImageSliderBinding.inflate(layoutInflater)
                return SliderViewHolder(binding)
            }
        }
    }

}

