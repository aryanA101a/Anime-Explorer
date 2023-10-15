package com.aryan.animeexplorer.data.local.dao

import androidx.paging.PagingSource
import androidx.room.Dao
import androidx.room.Query
import androidx.room.Upsert
import com.aryan.animeexplorer.data.local.entity.AnimeTitleEntity
import com.aryan.animeexplorer.data.local.entity.FavouritesEntity
import com.aryan.animeexplorer.domain.model.AnimeTitleType
import kotlinx.coroutines.flow.Flow

@Dao
interface AnimeTitlesDao {
    @Upsert()
    suspend fun upsertAll(animeTitles: List<AnimeTitleEntity>)

    @Query("SELECT * FROM ${AnimeTitleEntity.TABLE_NAME} WHERE type=:type")
    fun pagingSource(type: AnimeTitleType): PagingSource<Int, AnimeTitleEntity>

    @Query("DELETE FROM ${AnimeTitleEntity.TABLE_NAME}")
    suspend fun clearAll()



}