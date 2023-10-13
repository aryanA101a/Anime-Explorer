package com.aryan.animeexplorer.domain

import com.aryan.animeexplorer.data.local.AnimeTitleResult

interface AnimeClient {
    suspend fun getAnimeTitleResults(page:Int, perPage:Int): List<AnimeTitleResult>
}