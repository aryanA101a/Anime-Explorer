package com.aryan.animeexplorer.data.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(
    tableName = AnimeDetailsEntity.TABLE_NAME
)
data class AnimeDetailsEntity(
    @PrimaryKey
    val id: Int,
    val title: String?,
    val romanjiTitle: String?,
    val description: String?,
    val bannerImage: String?,
    val imageUrl: String?,
    val color: String?,
    val format: String?,
    val episodes: Int?,
    val duration: Int?,
    val status: String?,
    val startYear: String?,
    val meanScore: Int?,
    val genres: String?,
){
    companion object {
        const val TABLE_NAME = "anime_details"
    }
}