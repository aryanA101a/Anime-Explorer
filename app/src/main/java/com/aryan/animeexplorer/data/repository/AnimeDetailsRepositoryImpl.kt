package com.aryan.animeexplorer.data.repository

import android.util.Log
import com.aryan.animeexplorer.data.local.CacheDatabase
import com.aryan.animeexplorer.data.mappers.toAnimeDetails
import com.aryan.animeexplorer.data.mappers.toAnimeDetailsEntity
import com.aryan.animeexplorer.domain.AnimeClient
import com.aryan.animeexplorer.domain.model.AnimeDetails
import com.aryan.animeexplorer.domain.repository.AnimeDetailsRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import java.io.InvalidObjectException

class AnimeDetailsRepositoryImpl(
    private val animeClient: AnimeClient,
    private val cacheDatabase: CacheDatabase
) : AnimeDetailsRepository {
    override fun getAnimeDetails(id: Int): Flow<AnimeDetails?> = flow {
        var remoteFlag = false
        var dbFlag = false

        val animeDetails = cacheDatabase.animeDetailsDao.getAnimeDetails(id)?.toAnimeDetails()
        animeDetails?.let {
            emit(it)
            dbFlag = true
        }

        val remoteAnimeDetails = animeClient.getAnimeDetails(id)
        remoteAnimeDetails?.let {
            cacheDatabase.animeDetailsDao.delete(id)
            cacheDatabase.animeDetailsDao.upsert(it.toAnimeDetailsEntity())

            val newAnimeDetails =
                cacheDatabase.animeDetailsDao.getAnimeDetails(id)?.toAnimeDetails()
            emit(newAnimeDetails)
            remoteFlag = true
        }
        if (!remoteFlag && !dbFlag)
            emit(null)


    }
}
