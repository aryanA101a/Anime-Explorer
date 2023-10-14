package com.aryan.animeexplorer.data.local.dao

import androidx.room.Dao
import androidx.room.Query
import androidx.room.Upsert
import com.aryan.animeexplorer.data.local.entity.AnimeDetailsEntity
import com.aryan.animeexplorer.domain.model.AnimeDetails

@Dao
interface AnimeDetailsDao {
    @Upsert
    suspend fun upsert(animeDetails: AnimeDetailsEntity)

    @Query("DELETE FROM ${AnimeDetailsEntity.TABLE_NAME} WHERE id=:id")
    suspend fun delete(id: Int)

    @Query("SELECT * FROM ${AnimeDetailsEntity.TABLE_NAME} WHERE id=:id")
    suspend fun getAnimeDetails(id: Int): AnimeDetailsEntity?
}