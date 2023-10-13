package com.aryan.animeexplorer.domain

import com.aryan.animeexplorer.data.local.AnimeTitlesResult
import com.aryan.type.MediaSort

interface AnimeClient {
    suspend fun getAnimeTitles(page:Int, perPage:Int, sort: MediaSort): AnimeTitlesResult?
    suspend fun getSearchedAnimeTitles(page:Int, perPage:Int,searchQuery: String):AnimeTitlesResult?
}