package com.aryan.animeexplorer.presentation

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.aryan.animeexplorer.data.local.CacheDatabase
import com.aryan.animeexplorer.data.mappers.toFavouritesEntity
import com.aryan.animeexplorer.domain.model.AnimeDetails
import com.aryan.animeexplorer.domain.repository.AnimeDetailsRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AnimeDetailsViewModel @Inject constructor(
    private val animeDetailsRepository: AnimeDetailsRepository,
    private val cacheDatabase: CacheDatabase
) :
    ViewModel() {

    private val _isFavourite = MutableStateFlow<Boolean>(false)
    val isFavourite: StateFlow<Boolean> = _isFavourite

    private val _animeDetailsState = MutableStateFlow(AnimeDetails(0))
    val animeDetailsState: StateFlow<AnimeDetails> = _animeDetailsState

    private val _uiState: MutableStateFlow<AnimeDetailsUiStates> =
        MutableStateFlow(AnimeDetailsUiStates.Initial())
    val uiState: StateFlow<AnimeDetailsUiStates> = _uiState

    fun loadAnimeDetails(id: Int, title: String?){
        _animeDetailsState.update { it.copy(id = id.toInt(), title = title) }
        retrieveAnimeDetails(id)
    }

    fun retrieveAnimeDetails(id:Int){
        try {
            viewModelScope.launch {
                //init isFavourite
                cacheDatabase.favouritesDao.isFavourite(id)?.let {
                    _isFavourite.update { true }
                }
                _uiState.update {
                    AnimeDetailsUiStates.ShowIsFavourite()
                }

                //retrieve anime details
                animeDetailsRepository.getAnimeDetails(id.toInt()).collect { animeDetails ->
                    Log.i("TAG", "setAnimeDetails: $animeDetails")
                    if (animeDetails != null) {
                        _animeDetailsState.update { animeDetails }
                        animeDetails.bannerImage?.let { bannerImage ->
                            _uiState.update {
                                AnimeDetailsUiStates.Success(
                                    bannerImageUrl = bannerImage
                                )
                            }
                        }
                    } else
                        _uiState.update { AnimeDetailsUiStates.Error("Something went wrong") }
                }
            }
        } catch (e: Exception) {
            Log.e("TAG", "setAnimeDetails: $e")
        }
    }

    fun toggleFavourite() {
        try {
            _isFavourite.update { !_isFavourite.value }
            viewModelScope.launch {
                if (_isFavourite.value)
                    cacheDatabase.favouritesDao.markAsFavourite(animeDetailsState.value.toFavouritesEntity())
                else
                    cacheDatabase.favouritesDao.unMarkAsFavourite(animeDetailsState.value.id)

            }
        } catch (e: Exception) {
            Log.e("TAG", "setIsFavourite: $e")
        }
    }

    sealed class AnimeDetailsUiStates {
        data class Initial(val message: String? = null) : AnimeDetailsUiStates()
        data class Error(val message: String) : AnimeDetailsUiStates()
        data class Success(val bannerImageUrl: String) : AnimeDetailsUiStates()
        data class ShowIsFavourite(val data: Boolean? = null) : AnimeDetailsUiStates()
    }
}