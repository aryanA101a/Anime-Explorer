package com.aryan.animeexplorer.data.mappers

import android.util.Log
import com.aryan.AnimeTitlesQuery
import com.aryan.SearchAnimeTitlesQuery
import com.aryan.animeexplorer.data.remote.dto.AnimeTitlesResult
import com.aryan.animeexplorer.data.local.entity.AnimeTitleEntity
import com.aryan.animeexplorer.data.local.entity.FavouritesEntity
import com.aryan.animeexplorer.domain.model.AnimeDetails
import com.aryan.animeexplorer.domain.model.AnimeTitle
import com.aryan.animeexplorer.domain.model.AnimeTitleType

fun AnimeTitle.toAnimeTitleEntity(type: AnimeTitleType): AnimeTitleEntity {
    return AnimeTitleEntity(
        id = id,
        title = title,
        romanjiTitle=romanjiTitle,
        imageUrl = imageUrl,
        color = color,
        type = type,
    )

}

fun AnimeTitlesQuery.Page.toAnimeTitleResult(): AnimeTitlesResult {
    return AnimeTitlesResult(
        currentPage = pageInfo?.currentPage,
        hasNextPage = pageInfo?.hasNextPage,
        animeTitles = media!!.map { medium ->
            AnimeTitle(
                id = medium!!.id,
                title = medium.title?.english,
                romanjiTitle=medium.title?.romaji,
                imageUrl = medium.coverImage?.large,
                color = medium.coverImage?.color,
            )
        }
    ).also { Log.i("TAG", "toAnimeTitleResult: ${it.hasNextPage}") }


}

fun SearchAnimeTitlesQuery.Page.toAnimeTitleResult(): AnimeTitlesResult {
    return AnimeTitlesResult(
        currentPage = pageInfo?.currentPage,
        hasNextPage = pageInfo?.hasNextPage,
        animeTitles = media!!.map { medium ->
            AnimeTitle(
                id = medium!!.id,
                title = medium.title?.english,
                romanjiTitle=medium.title?.romaji,
                imageUrl = medium.coverImage?.large,
                color = medium.coverImage?.color,
            )
        }
    ).also { Log.i("TAG", "toAnimeTitleResult: ${it.hasNextPage}") }


}


fun AnimeTitleEntity.toAnimeTitle(): AnimeTitle {
    return AnimeTitle(
        id = id,
        title = title,
        romanjiTitle=romanjiTitle,
        imageUrl = imageUrl,
        color = color,
    )
}




