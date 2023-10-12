package com.aryan.animeexplorer.view.adapter

import android.graphics.Color
import android.net.Uri
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.ImageView
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import coil.load
import coil.size.Scale
import com.aryan.animeexplorer.R
import com.aryan.animeexplorer.databinding.AnimeTitleBinding
import com.aryan.animeexplorer.domain.AnimeTitle

class AnimeTitlesAdapter() : ListAdapter<AnimeTitle, MyViewHolder>(MyDiffCallback()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        return MyViewHolder(
            DataBindingUtil.inflate(
                LayoutInflater.from(parent.context),
                R.layout.anime_title,
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        holder.bind(getItem(position))
    }


}

class MyViewHolder(val binding: AnimeTitleBinding) : RecyclerView.ViewHolder(binding.root) {
    fun bind(data: AnimeTitle) {

        binding.apply {
            ivAnimeTitle.setBackgroundColor(Color.parseColor(data.coverImage.color))
            ivAnimeTitle.scaleType= ImageView.ScaleType.CENTER_CROP
            ivAnimeTitle.load(data.coverImage.image){
                crossfade(true)
            }
            tvAnimeTitle.text = data.title

        }

    }


}

class MyDiffCallback : DiffUtil.ItemCallback<AnimeTitle>() {
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
        return oldItem.title == newItem.title && oldItem.coverImage == newItem.coverImage
    }

}