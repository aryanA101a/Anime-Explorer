package com.aryan.animeexplorer.data.local.entity

import androidx.room.Entity
import androidx.room.Index
import androidx.room.PrimaryKey
import com.aryan.animeexplorer.domain.AnimeTitleType

@Entity(tableName = AnimeTitleEntity.TABLE_NAME, indices =[Index(value = ["type"])] )
data class AnimeTitleEntity(
    @PrimaryKey(autoGenerate = true)
    val newId: Int = 0,
    val id: Int,
    val title: String?,
    val imageUrl: String?,
    val color: String?,
    val type: AnimeTitleType
) {
    companion object {
        const val TABLE_NAME = "anime_title"
    }
}

