package com.example.material.tvshowexperiment.ui.detail

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.material.tvshowexperiment.data.Episode
import com.example.material.tvshowexperiment.databinding.ItemEpisodesViewBinding

class EpisodeAdapter : RecyclerView.Adapter<EpisodeAdapter.EpisodeViewHolder>() {

    var data = listOf<Episode>()
        set(value) {
            field = value
            notifyDataSetChanged()
        }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): EpisodeViewHolder {
        return EpisodeViewHolder.from(parent)
    }

    override fun onBindViewHolder(holder: EpisodeViewHolder, position: Int) {
        val items = data[position]
        holder.bind(items)
    }

    override fun getItemCount(): Int = data.size

    class EpisodeViewHolder(val binding: ItemEpisodesViewBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(items: Episode?) {
            if (items != null) {
                binding.episodes = items
                var title: String = "S"
                var season: String = items.season.toString()
                if (season.length == 1) {
                    season = "0$season"
                }
                var episodeNumber = items.episode.toString()
                if (episodeNumber.length == 1) {
                    episodeNumber = "0$episodeNumber"
                }
                episodeNumber = "E${episodeNumber}"
                title = title + season + episodeNumber
                binding.textTitleEpisodes.text = title
            }
        }

        companion object {
            fun from(parent: ViewGroup): EpisodeViewHolder {
                val layoutInflater = LayoutInflater.from(parent.context)
                val binding = ItemEpisodesViewBinding.inflate(layoutInflater, parent, false)
                return EpisodeViewHolder(binding)
            }
        }
    }

}

