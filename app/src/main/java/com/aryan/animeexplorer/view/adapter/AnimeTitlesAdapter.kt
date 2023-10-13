package com.aryan.animeexplorer.view.adapter

import android.graphics.Color
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.ImageView
import androidx.databinding.DataBindingUtil
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import coil.load
import com.aryan.animeexplorer.R
import com.aryan.animeexplorer.databinding.AnimeTitleBinding
import com.aryan.animeexplorer.domain.AnimeTitle

class AnimeTitlesAdapter() :
    PagingDataAdapter<AnimeTitle, AnimeTitleViewHolder>(AnimeTitleDiffCallback()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AnimeTitleViewHolder {
        return AnimeTitleViewHolder(
            DataBindingUtil.inflate(
                LayoutInflater.from(parent.context),
                R.layout.anime_title,
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: AnimeTitleViewHolder, position: Int) {
        getItem(position)?.let { holder.bind(it) }
    }


}

class AnimeTitleViewHolder(val binding: AnimeTitleBinding) : RecyclerView.ViewHolder(binding.root) {
    fun bind(data: AnimeTitle) {

        binding.apply {
            data.color?.let{ivAnimeTitle.setBackgroundColor(Color.parseColor(it))}
            ivAnimeTitle.scaleType = ImageView.ScaleType.CENTER_CROP
            data.imageUrl?.let { ivAnimeTitle.load(it) {
                crossfade(true)
            } }
            tvAnimeTitle.text = data.title?:""

        }

    }


}

class AnimeTitleDiffCallback : DiffUtil.ItemCallback<AnimeTitle>() {
    override fun areItemsTheSame(
        oldItem: AnimeTitle,
        newItem: AnimeTitle
    ): Boolean {
        return oldItem.id == newItem.id
    }

    override fun areContentsTheSame(
        oldItem: AnimeTitle,
        newItem: AnimeTitle
    ): Boolean {
        return oldItem.title == newItem.title && oldItem.imageUrl == newItem.imageUrl
    }

}