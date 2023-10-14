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

class SearchAnimeTitlesAdapter(val onViewItemClicked: (Int, String) -> Unit) :
    ListAdapter<AnimeTitle, SearchAnimeTitlesAdapter.SearchAnimeTitleViewHolder>(SearchAnimeTitleDiffCallback()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SearchAnimeTitleViewHolder {
        return SearchAnimeTitleViewHolder(
            DataBindingUtil.inflate(
                LayoutInflater.from(parent.context),
                R.layout.anime_title,
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: SearchAnimeTitleViewHolder, position: Int) {
        getItem(position)?.let { holder.bind(it) }
    }

    inner class SearchAnimeTitleViewHolder(val binding: AnimeTitleBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(data: AnimeTitle) {

            binding.apply {
                cvAnimeTitle.setOnClickListener {
                    onViewItemClicked(data.id, data.title ?: "")
                }
                data.color?.let{ivAnimeTitle.setBackgroundColor(Color.parseColor(it))}
                ivAnimeTitle.scaleType = ImageView.ScaleType.CENTER_CROP
                data.imageUrl?.let { ivAnimeTitle.load(it) {
                    crossfade(true)
                } }
                tvAnimeTitle.text = data.title?:""

            }

        }


    }

}



class SearchAnimeTitleDiffCallback : DiffUtil.ItemCallback<AnimeTitle>() {

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