package com.aryan.animeexplorer.data.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = FavouritesEntity.TABLE_NAME)
data class FavouritesEntity(
    @PrimaryKey
    val id: Int,
    val title: String?,
    val romanjiTitle:String?,
    val imageUrl: String?,
    val color: String?,
) {
    companion object {
        const val TABLE_NAME = "favourites"
    }
}