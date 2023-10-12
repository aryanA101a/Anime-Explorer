package com.aryan.animeexplorer.data

import com.apollographql.apollo3.ApolloClient
import com.apollographql.apollo3.api.Optional
import com.aryan.AnimeTitlesQuery
import com.aryan.animeexplorer.domain.AnimeClient
import com.aryan.animeexplorer.domain.AnimeTitle
import com.aryan.type.MediaSort
import javax.inject.Inject

class ApolloAnimeClient @Inject constructor(private val apolloClient: ApolloClient) : AnimeClient {
    override suspend fun getAnimeTitles(
        page: Int,
        perPage: Int,
        sort: MediaSort
    ): List<AnimeTitle> {
        return apolloClient.query(
            AnimeTitlesQuery(
                page = Optional.present(page),
                perPage = Optional.present(perPage),
                sort = Optional.present(sort)
            )
        ).execute().data?.Page?.media?.filterNotNull()
            ?.map { medium -> medium.toAnimeTitle() } ?: emptyList()

    }

}