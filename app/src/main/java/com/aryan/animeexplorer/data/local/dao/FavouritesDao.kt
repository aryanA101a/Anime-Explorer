package com.aryan.animeexplorer.data.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Upsert
import com.aryan.animeexplorer.data.local.entity.AnimeTitleEntity
import com.aryan.animeexplorer.data.local.entity.FavouritesEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface FavouritesDao {
    @Upsert
    suspend fun markAsFavourite(favouritesEntity: FavouritesEntity)

    @Query("DELETE FROM ${FavouritesEntity.TABLE_NAME} where id = :id")
    suspend fun unMarkAsFavourite(id:Int)

    @Query("SELECT * FROM ${FavouritesEntity.TABLE_NAME} WHERE id=:id")
    suspend fun isFavourite(id:Int): FavouritesEntity?

    @Query("SELECT * FROM ${FavouritesEntity.TABLE_NAME}")
    fun getFavourites(): Flow<List<FavouritesEntity>>
}