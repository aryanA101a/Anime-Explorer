package com.aryan.animeexplorer.domain.repository

import com.aryan.animeexplorer.domain.model.AnimeDetails
import kotlinx.coroutines.flow.Flow

interface AnimeDetailsRepository {
     fun getAnimeDetails(id: Int): Flow<AnimeDetails?>
}