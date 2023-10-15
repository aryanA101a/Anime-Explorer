package com.aryan.animeexplorer.data.mappers

import com.aryan.animeexplorer.data.local.entity.FavouritesEntity
import com.aryan.animeexplorer.domain.model.FavouriteAnimeTitle

fun FavouritesEntity.toFavouriteAnimeTitle():FavouriteAnimeTitle {
    return FavouriteAnimeTitle(
        id = id,
        title = title,
        imageUrl = imageUrl,
        color = color,
    )
}