package com.aryan.animeexplorer.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.Pager
import androidx.paging.cachedIn
import androidx.paging.map
import com.aryan.animeexplorer.data.local.entity.AnimeTitleEntity
import com.aryan.animeexplorer.data.mappers.toAnimeTitle
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.map
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    pager: Pager<Int, AnimeTitleEntity>
) : ViewModel() {
    val trendingAnimeTitles =
        pager.flow.map { pagingData -> pagingData.map { it.toAnimeTitle() } }
            .cachedIn(viewModelScope)


}