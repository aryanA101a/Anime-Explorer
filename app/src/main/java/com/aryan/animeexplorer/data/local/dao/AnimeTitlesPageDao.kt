package com.aryan.animeexplorer.data.local.dao

import androidx.paging.PagingSource
import androidx.room.Dao
import androidx.room.Query
import androidx.room.Upsert
import com.aryan.animeexplorer.data.local.entity.AnimeTitleEntity

@Dao
interface AnimeTitlesPageDao {
    @Upsert
    suspend fun upsertAll(animeTitles:List<AnimeTitleEntity>)

    @Query("SELECT * FROM ${AnimeTitleEntity.TABLE_NAME}")
    fun pagingSource(): PagingSource<Int, AnimeTitleEntity>

    @Query("DELETE FROM ${AnimeTitleEntity.TABLE_NAME}")
    suspend fun clearAll()
}