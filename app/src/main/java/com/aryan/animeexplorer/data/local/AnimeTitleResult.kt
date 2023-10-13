package com.aryan.animeexplorer.data.local

data class AnimeTitleResult (
    val currentPage: Int?,
    val hasNextPage: Boolean?,
    val id: Int,
    val title: String?,
    val imageUrl: String?,
    val color: String?
)