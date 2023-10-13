package com.aryan.animeexplorer.data.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = PageKeyEntity.TABLE_NAME)
data class PageKeyEntity(
    @PrimaryKey
    val animeTitleID:Int,
    val currentPage: Int?,
    val hasNextPage: Boolean?,
){
    companion object {
        const val TABLE_NAME = "page_key"
    }
}