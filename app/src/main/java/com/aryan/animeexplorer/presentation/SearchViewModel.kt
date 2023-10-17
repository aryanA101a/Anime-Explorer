package com.aryan.animeexplorer.presentation

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.aryan.animeexplorer.domain.AnimeClient
import com.aryan.animeexplorer.domain.model.AnimeTitle
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SearchViewModel @Inject constructor(
    private val animeClient: AnimeClient
) : ViewModel() {

    private var _searchedAnimeTitles = MutableStateFlow<List<AnimeTitle>>(emptyList())
    val searchedAnimeTitles = _searchedAnimeTitles

    private val _uiState: MutableStateFlow<SearchUiStates> =
        MutableStateFlow(SearchUiStates.Initial())
    val uiState: StateFlow<SearchUiStates> = _uiState

    private var searchJob: Job? = null

    fun executeSearch(searchQuery: String) {
        searchJob?.cancel()
        if (searchQuery.isNotEmpty()) {
            searchJob = viewModelScope.launch(Dispatchers.IO) {
                delay(500L)
                val animeTitles: List<AnimeTitle>? = try {

                    animeClient.searchAnimeTitles(
                        1,
                        50,
                        searchQuery
                    )?.animeTitles


                } catch (e: Exception) {
                    Log.e("TAG", "executeSearch: $e")
                    null
                }
                Log.i("TAG", "executeSearch: ${searchQuery}")
                Log.i("TAG", "executeSearch: ${animeTitles?.size}")
                if (animeTitles == null) {
                    _searchedAnimeTitles.update { emptyList() }
                    _uiState.update { SearchUiStates.Error("Couldn't fetch anime titles") }
                } else {
                    animeTitles?.let { animeTitles ->
                        if (animeTitles.isNotEmpty()) {
                            _searchedAnimeTitles.update { animeTitles }
                            _uiState.update { SearchUiStates.Success() }
                        } else {
                            _searchedAnimeTitles.update { animeTitles }
                            _uiState.update { SearchUiStates.Empty() }
                        }
                    }
                }
            }
        } else {
            _searchedAnimeTitles.update { emptyList() }
        }

    }

    sealed class SearchUiStates {
        data class Initial(val message: String? = null) : SearchUiStates()
        data class Empty(val message: String? = null) : SearchUiStates()
        data class Success(val message: String? = null) : SearchUiStates()
        data class Error(val message: String) : SearchUiStates()

    }
}