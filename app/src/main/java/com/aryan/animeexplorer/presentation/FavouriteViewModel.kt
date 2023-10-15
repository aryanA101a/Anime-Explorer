package com.aryan.animeexplorer.presentation

import android.util.Log
import androidx.lifecycle.ViewModel
import com.aryan.animeexplorer.data.local.CacheDatabase
import com.aryan.animeexplorer.data.mappers.toAnimeTitle
import com.aryan.animeexplorer.data.mappers.toFavouriteAnimeTitle
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.map
import javax.inject.Inject

@HiltViewModel
class FavouriteViewModel @Inject constructor(cacheDatabase: CacheDatabase) : ViewModel() {
    init {
        Log.i("TAG", "initFavouriteViewModel: ")
    }
    val boo="siiko"
    val favourites = cacheDatabase.favouritesDao.getFavourites()
        .map { list -> list.map { animeTitle -> animeTitle.toFavouriteAnimeTitle() } }

}