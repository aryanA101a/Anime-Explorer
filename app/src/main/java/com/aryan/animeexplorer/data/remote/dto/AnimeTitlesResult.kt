package com.aryan.animeexplorer.data.remote.dto

import com.aryan.animeexplorer.domain.model.AnimeTitle

data class AnimeTitlesResult (
    val currentPage: Int?,
    val hasNextPage: Boolean?,
    val animeTitles:List<AnimeTitle>
)