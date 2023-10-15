package com.aryan.animeexplorer.view.adapter

import android.graphics.Color
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.ImageView
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import coil.load
import com.aryan.animeexplorer.R
import com.aryan.animeexplorer.databinding.AnimeTitleBinding
import com.aryan.animeexplorer.domain.model.AnimeTitle
import com.aryan.animeexplorer.domain.model.FavouriteAnimeTitle

class FavouriteAnimeTitlesAdapter(
    val onViewItemClicked: (Int, String) -> Unit
) :
    ListAdapter<FavouriteAnimeTitle, FavouriteAnimeTitlesAdapter.FavouriteAnimeTitleViewHolder>(FavouriteAnimeTitleDiffCallback()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FavouriteAnimeTitleViewHolder {
        return FavouriteAnimeTitleViewHolder(
            DataBindingUtil.inflate(
                LayoutInflater.from(parent.context),
                R.layout.anime_title,
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: FavouriteAnimeTitleViewHolder, position: Int) {
        getItem(position)?.let { holder.bind(it, position) }
    }

    inner class FavouriteAnimeTitleViewHolder(val binding: AnimeTitleBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(data: FavouriteAnimeTitle, position: Int) {

            binding.apply {
                cvAnimeTitle.setOnClickListener {
                    onViewItemClicked(data.id, data.title ?: "")
                }
                data.color?.let { ivAnimeTitle.setBackgroundColor(Color.parseColor(it)) }
                ivAnimeTitle.scaleType = ImageView.ScaleType.CENTER_CROP
                data.imageUrl?.let {
                    ivAnimeTitle.load(it) {
                        crossfade(true)
                    }
                }
                tvAnimeTitle.text = data.title ?: ""

            }

        }


    }

}


class FavouriteAnimeTitleDiffCallback : DiffUtil.ItemCallback<FavouriteAnimeTitle>() {
    override fun areItemsTheSame(
        oldItem: FavouriteAnimeTitle,
        newItem: FavouriteAnimeTitle
    ): Boolean {
        return oldItem.id == newItem.id
    }

    override fun areContentsTheSame(
        oldItem: FavouriteAnimeTitle,
        newItem: FavouriteAnimeTitle
    ): Boolean {
        return oldItem.title == newItem.title && oldItem.imageUrl == newItem.imageUrl
    }

}