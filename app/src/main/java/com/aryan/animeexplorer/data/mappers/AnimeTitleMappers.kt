package com.aryan.animeexplorer.data.mappers

import android.util.Log
import com.aryan.AnimeTitlesQuery
import com.aryan.SearchAnimeTitlesQuery
import com.aryan.animeexplorer.data.local.AnimeTitlesResult
import com.aryan.animeexplorer.data.local.entity.AnimeTitleEntity
import com.aryan.animeexplorer.domain.AnimeTitle
import com.aryan.animeexplorer.domain.AnimeTitleType

fun AnimeTitle.toAnimeTitleEntity(type:AnimeTitleType): AnimeTitleEntity {
    return AnimeTitleEntity(
        id = id,
        title = title,
        imageUrl = imageUrl,
        color = color,
        type = type
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
                imageUrl = medium.coverImage?.large,
                color = medium.coverImage?.color
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
                imageUrl = medium.coverImage?.large,
                color = medium.coverImage?.color
            )
        }
    ).also { Log.i("TAG", "toAnimeTitleResult: ${it.hasNextPage}") }


}


fun AnimeTitleEntity.toAnimeTitle(): AnimeTitle {
    return AnimeTitle(
        id = id,
        title = title,
        imageUrl = imageUrl,
        color = color
    )
}


