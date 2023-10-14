package com.aryan.animeexplorer.domain

import com.aryan.AnimeDetailsQuery
import com.aryan.animeexplorer.data.remote.dto.AnimeTitlesResult
import com.aryan.animeexplorer.domain.model.AnimeDetails
import com.aryan.type.MediaSort

interface AnimeClient {
    suspend fun getAnimeTitles(page: Int, perPage: Int, sort: MediaSort): AnimeTitlesResult?
    suspend fun searchAnimeTitles(page: Int, perPage: Int, searchQuery: String): AnimeTitlesResult?
    suspend fun getAnimeDetails(id: Int): AnimeDetailsQuery.Media?
}