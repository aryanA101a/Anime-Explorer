package com.aryan.animeexplorer.data.mappers

import com.aryan.AnimeTitlesQuery
import com.aryan.animeexplorer.data.local.AnimeTitleResult
import com.aryan.animeexplorer.data.local.entity.AnimeTitleEntity
import com.aryan.animeexplorer.domain.AnimeTitle

fun AnimeTitleResult.toAnimeTitleEntity(): AnimeTitleEntity {
    return AnimeTitleEntity(
        id = id,
        title = title,
        imageUrl = imageUrl,
        color = color
    )

}

fun AnimeTitlesQuery.Page.toAnimeTitleResults(): List<AnimeTitleResult> {
    return media!!.map { medium ->
        AnimeTitleResult(
            currentPage = pageInfo?.currentPage,
            hasNextPage = pageInfo?.hasNextPage,
            id = medium!!.id,
            title = medium.title?.english,
            imageUrl = medium.coverImage?.large,
            color = medium.coverImage?.color
        )
    }
}

fun AnimeTitleEntity.toAnimeTitle(): AnimeTitle {
    return AnimeTitle(
        id = id,
        title = title,
        imageUrl = imageUrl,
        color = color
    )
}


