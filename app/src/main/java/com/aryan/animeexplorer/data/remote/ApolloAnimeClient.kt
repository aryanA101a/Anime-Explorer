package com.aryan.animeexplorer.data.remote

import android.util.Log
import com.apollographql.apollo3.ApolloClient
import com.apollographql.apollo3.api.Optional
import com.aryan.AnimeDetailsQuery
import com.aryan.AnimeTitlesQuery
import com.aryan.SearchAnimeTitlesQuery
import com.aryan.animeexplorer.data.mappers.toAnimeDetails
import com.aryan.animeexplorer.data.mappers.toAnimeTitleResult
import com.aryan.animeexplorer.data.remote.dto.AnimeTitlesResult
import com.aryan.animeexplorer.domain.AnimeClient
import com.aryan.animeexplorer.domain.model.AnimeDetails
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
                //condition to only allow 100 results on the top 100 anime title type
                if (sort == MediaSort.SCORE_DESC && it.currentPage!! > 100 / pageSize)
                    return it?.copy(animeTitles = it.animeTitles.subList(0, 4), hasNextPage = false)
                else it
            }

    }


    override suspend fun searchAnimeTitles(
        page: Int,
        perPage: Int,
        searchQuery: String
    ): AnimeTitlesResult? {
        return apolloClient.query(
            SearchAnimeTitlesQuery(
                page = Optional.present(page),
                perPage = Optional.present(perPage),
                search = Optional.present(searchQuery)
            )
        ).execute()
            .takeIf { apolloResponse -> !apolloResponse.hasErrors() }
            ?.data
            ?.Page
            ?.takeIf { page -> page.pageInfo != null && page.media != null }
            ?.toAnimeTitleResult()

    }

    override suspend fun getAnimeDetails(
        id: Int
    ): AnimeDetailsQuery.Media? {
        return try {
            apolloClient.query(
                AnimeDetailsQuery(
                    id = Optional.present(id)
                )
            ).execute()
                .takeIf { apolloResponse -> !apolloResponse.hasErrors() }
                ?.data
                ?.Media
        } catch (e: Exception) {
            Log.e("TAG", "getAnimeDetails: $e")
            null
        }
    }

    companion object {
        const val pageSize: Int = 16
    }

}