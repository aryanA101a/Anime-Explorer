package com.aryan.animeexplorer.data.remote

import com.apollographql.apollo3.ApolloClient
import com.apollographql.apollo3.api.Optional
import com.aryan.AnimeTitlesQuery
import com.aryan.animeexplorer.data.local.AnimeTitlesResult
import com.aryan.animeexplorer.data.mappers.toAnimeTitleResult
import com.aryan.animeexplorer.domain.AnimeClient
import com.aryan.type.MediaSort
import javax.inject.Inject

class ApolloAnimeClient @Inject constructor(private val apolloClient: ApolloClient) : AnimeClient {
    override suspend fun getAnimeTitles(
        page: Int,
        perPage: Int,
        sort: MediaSort
    ): AnimeTitlesResult? {
        return apolloClient.query(
            AnimeTitlesQuery(
                page = Optional.present(page),
                perPage = Optional.present(perPage),
                sort = Optional.present(sort)
            )
        ).execute()
            .takeIf { apolloResponse -> !apolloResponse.hasErrors() }
            ?.data
            ?.Page
            ?.takeIf { page -> page.pageInfo != null && page.media != null }
            ?.toAnimeTitleResult()?.let {
                //condition to only allow near 100 results on the top 100 anime title type
                if (sort == MediaSort.SCORE_DESC && it.currentPage!! > 100 / pageSize)
                    return it?.copy(hasNextPage = false)
                else it
            }

    }

    companion object {
        const val pageSize: Int = 16
    }

}