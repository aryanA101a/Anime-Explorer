package com.aryan.animeexplorer.data.local.dao

import androidx.room.Dao
import androidx.room.Query
import androidx.room.Upsert
import com.aryan.animeexplorer.data.local.entity.PageKeyEntity

@Dao
interface PageKeyDao {
    @Upsert
    suspend fun upsertAll(pageKeys:List<PageKeyEntity>)

    @Query("SELECT * FROM ${PageKeyEntity.TABLE_NAME} WHERE animeTitleID = :id")
    suspend fun pageKey(id:Int): PageKeyEntity

    @Query("DELETE FROM ${PageKeyEntity.TABLE_NAME}")
    suspend fun clearAll()
}