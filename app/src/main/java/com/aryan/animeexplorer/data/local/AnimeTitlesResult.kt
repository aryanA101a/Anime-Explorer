package com.aryan.animeexplorer.data.local

import com.aryan.animeexplorer.domain.AnimeTitle

data class AnimeTitlesResult (
    val currentPage: Int?,
    val hasNextPage: Boolean?,
    val animeTitles:List<AnimeTitle>
)