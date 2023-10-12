package com.aryan.animeexplorer.domain

import com.aryan.type.MediaSort

interface AnimeClient {
    suspend fun getAnimeTitles(page:Int,perPage:Int,sort:MediaSort): List<AnimeTitle>
}