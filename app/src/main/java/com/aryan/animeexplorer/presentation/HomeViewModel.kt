package com.aryan.animeexplorer.presentation

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.aryan.animeexplorer.domain.AnimeClient
import com.aryan.animeexplorer.domain.AnimeTitle
import com.aryan.type.MediaSort
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val animeClient: AnimeClient
) : ViewModel() {
    private val _animeTitlesState = MutableStateFlow(AnimeTitlesState())
    val animeTitlesState = _animeTitlesState.asStateFlow()

    init {
        getAnimeTitles(page = 0, perPage = 16, sort = MediaSort.TRENDING)
    }

    data class AnimeTitlesState(
        val animeTitles: List<AnimeTitle> = emptyList(),
        val isLoading: Boolean = false
    )

    private fun getAnimeTitles(
        page: Int,
        perPage: Int,
        sort: MediaSort
    ) {
        viewModelScope.launch {
            _animeTitlesState.update { it.copy(isLoading = true) }
            _animeTitlesState.update {
                it.copy(
                    animeTitles = animeClient.getAnimeTitles(
                        page = page,
                        perPage = perPage,
                        sort = sort
                    ),
                    isLoading = false
                )
            }

        }
    }
}