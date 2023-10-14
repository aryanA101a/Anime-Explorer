package com.aryan.animeexplorer.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.aryan.animeexplorer.domain.AnimeClient
import com.aryan.animeexplorer.domain.model.AnimeTitle
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
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
                    animeClient.searchAnimeTitles(
                        1,
                        50,
                        searchQuery
                    )?.animeTitles ?: emptyList()
                }
            }

    }
}