package com.aryan.animeexplorer.data.mappers

import com.aryan.animeexplorer.data.local.entity.FavouritesEntity
import com.aryan.animeexplorer.domain.model.AnimeDetails
import com.aryan.animeexplorer.domain.model.AnimeTitle

fun FavouritesEntity.toAnimeTitle(): AnimeTitle {
    return AnimeTitle(
        id = id,
        title = title,
        romanjiTitle=romanjiTitle,
        imageUrl = imageUrl,
        color = color,
    )
}

fun AnimeDetails.toFavouritesEntity(): FavouritesEntity {
    return FavouritesEntity(
        id = id,
        title = title,
        romanjiTitle=romanjiTitle,
        imageUrl = imageUrl,
        color = color,
    )
}