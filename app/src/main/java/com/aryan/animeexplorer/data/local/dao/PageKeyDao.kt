package com.aryan.animeexplorer.data.local.dao

import androidx.room.Dao
import androidx.room.Query
import androidx.room.Upsert
import com.aryan.animeexplorer.data.local.entity.PageKeyEntity
import com.aryan.animeexplorer.domain.AnimeTitleType

@Dao
interface PageKeyDao {
    @Upsert
    suspend fun upsertAll(pageKeys:List<PageKeyEntity>)

    @Query("SELECT * FROM ${PageKeyEntity.TABLE_NAME} WHERE animeTitleID = :id AND type = :type ")
    suspend fun pageKey(id:Int,type:AnimeTitleType): PageKeyEntity

    @Query("DELETE FROM ${PageKeyEntity.TABLE_NAME}")
    suspend fun clearAll()
}