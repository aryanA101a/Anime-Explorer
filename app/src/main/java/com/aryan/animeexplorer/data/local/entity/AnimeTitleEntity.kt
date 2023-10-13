package com.aryan.animeexplorer.data.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = AnimeTitleEntity.TABLE_NAME)
data class AnimeTitleEntity(
    @PrimaryKey(autoGenerate = true)
    val newId:Int=0,
    val id: Int,
    val title: String?,
    val imageUrl: String?,
    val color: String?
)
{
    companion object {
        const val TABLE_NAME = "anime_title"
    }
}

