package com.aryan.animeexplorer.data.remote

import com.apollographql.apollo3.ApolloClient
import com.apollographql.apollo3.api.Optional
import com.aryan.AnimeTitlesQuery
import com.aryan.animeexplorer.data.local.AnimeTitleResult
import com.aryan.animeexplorer.data.mappers.toAnimeTitleResults
import com.aryan.animeexplorer.domain.AnimeClient
import com.aryan.type.MediaSort
import javax.inject.Inject

class ApolloAnimeClient @Inject constructor(private val apolloClient: ApolloClient) : AnimeClient {
    override suspend fun getAnimeTitleResults(
        page: Int,
        perPage: Int,
    ): List<AnimeTitleResult> {
        return apolloClient.query(
            AnimeTitlesQuery(
                page = Optional.present(page),
                perPage = Optional.present(perPage),
                sort = Optional.present(MediaSort.TRENDING_DESC)
            )
        ).execute()
            .takeIf { apolloResponse -> !apolloResponse.hasErrors() }
            ?.data
            ?.Page
            ?.takeIf { page -> page.pageInfo != null }
            ?.toAnimeTitleResults() ?: emptyList()

    }

}