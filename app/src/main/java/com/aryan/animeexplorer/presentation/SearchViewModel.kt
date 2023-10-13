package com.aryan.animeexplorer.presentation

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.ExperimentalPagingApi
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.cachedIn
import androidx.paging.map
import com.aryan.animeexplorer.data.local.CacheDatabase
import com.aryan.animeexplorer.data.mappers.toAnimeTitle
import com.aryan.animeexplorer.data.remote.AnimeTitleRemoteMediator
import com.aryan.animeexplorer.data.remote.ApolloAnimeClient
import com.aryan.animeexplorer.domain.AnimeClient
import com.aryan.animeexplorer.domain.AnimeTitle
import com.aryan.animeexplorer.domain.AnimeTitleType
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SearchViewModel @Inject constructor(
    private val animeClient: AnimeClient
) : ViewModel() {

    private var _searchedAnimeTitles = MutableStateFlow<List<AnimeTitle>>(emptyList())
    val searchedAnimeTitles = _searchedAnimeTitles

    private var searchJob: Job? = null

    fun executeSearch(searchQuery: String) {
        searchJob?.cancel()
            searchJob=viewModelScope.launch(Dispatchers.IO) {
                delay(500L)
                _searchedAnimeTitles.update {
                    animeClient.getSearchedAnimeTitles(
                        1,
                        50,
                        searchQuery
                    )?.animeTitles ?: emptyList()
                }
            }

    }
}