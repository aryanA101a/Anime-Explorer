package com.aryan.animeexplorer.data.mappers

import android.text.Html
import com.aryan.AnimeDetailsQuery
import com.aryan.animeexplorer.data.local.entity.AnimeDetailsEntity
import com.aryan.animeexplorer.data.local.entity.FavouritesEntity
import com.aryan.animeexplorer.domain.model.AnimeDetails
import com.aryan.animeexplorer.domain.model.FavouriteAnimeTitle


fun AnimeDetailsEntity.toAnimeDetails(): AnimeDetails {
    return AnimeDetails(
        id = id,
        title = title,
        romanjiTitle = romanjiTitle,
        description = description,
        bannerImage = bannerImage,
        imageUrl = imageUrl,
        color = color,
        format = format,
        episodes = episodes,
        duration = duration,
        status = status,
        startYear = startYear,
        meanScore = meanScore,
        genres = genres
    )
}

fun AnimeDetailsQuery.Media.toAnimeDetailsEntity(): AnimeDetailsEntity {
    return AnimeDetailsEntity(
        id = id,
        title = title?.english,
        romanjiTitle = title?.romaji,
        description = if (description != null) Html.fromHtml(
            description,
            Html.FROM_HTML_MODE_LEGACY
        ).toString() else null,
        bannerImage = bannerImage,
        imageUrl = coverImage?.large,
        color = coverImage?.color,
        format = format?.rawValue,
        episodes = episodes,
        duration = duration,
        status = status?.rawValue,
        startYear = startDate?.year?.toString(),
        meanScore = meanScore,
        genres = genres?.joinToString(", ")
    )


}

fun AnimeDetails.toFavouriteAnimeTitle():FavouritesEntity{
    return FavouritesEntity(
        id = id,
        title = title,
        imageUrl = imageUrl,
        color = color,
    )
}