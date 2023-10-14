package com.aryan.animeexplorer.data.local.entity

import androidx.room.Entity
import com.aryan.animeexplorer.domain.model.AnimeTitleType

@Entity(tableName = PageKeyEntity.TABLE_NAME, primaryKeys = ["animeTitleID","type"])
data class PageKeyEntity(
    val animeTitleID:Int,
    val currentPage: Int?,
    val hasNextPage: Boolean?,
    val type: AnimeTitleType
){
    companion object {
        const val TABLE_NAME = "page_key"
    }
}