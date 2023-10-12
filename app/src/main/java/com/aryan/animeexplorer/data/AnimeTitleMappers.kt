package com.aryan.animeexplorer.data

import com.aryan.AnimeTitlesQuery
import com.aryan.animeexplorer.domain.AnimeTitle
import com.aryan.animeexplorer.domain.CoverImage

fun AnimeTitlesQuery.Medium.toAnimeTitle(): AnimeTitle {
    return AnimeTitle(
        id = id,
        title = title?.english ?: "Title",
        coverImage = coverImage?.toCoverImage() ?: CoverImage(
            image = "",
            color = "#3DDC84"
        )
    )
}

fun AnimeTitlesQuery.CoverImage.toCoverImage(): CoverImage {
    return CoverImage(
        image = large ?: "",
        color = color ?: "#3DDC84"
    )
}