package com.aryan.animeexplorer.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.Pager
import androidx.paging.cachedIn
import androidx.paging.map
import com.aryan.animeexplorer.data.local.entity.AnimeTitleEntity
import com.aryan.animeexplorer.data.mappers.toAnimeTitle
import com.aryan.animeexplorer.di.PopularAnimeTitlesPager
import com.aryan.animeexplorer.di.Top100AnimeTitlesPager
import com.aryan.animeexplorer.di.TrendingAnimeTitlesPager
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.map
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    @TrendingAnimeTitlesPager trendingAnimeTitlesPager: Pager<Int, AnimeTitleEntity>,
    @PopularAnimeTitlesPager popularAnimeTitlesPager: Pager<Int, AnimeTitleEntity>,
    @Top100AnimeTitlesPager topAnimeTitlesPager: Pager<Int, AnimeTitleEntity>
) : ViewModel() {


    val trendingAnimeTitles =
        trendingAnimeTitlesPager.flow.map { pagingData -> pagingData.map { it.toAnimeTitle() } }
            .cachedIn(viewModelScope)
    val popularAnimeTitles =
        popularAnimeTitlesPager.flow.map { pagingData -> pagingData.map { it.toAnimeTitle() } }
            .cachedIn(viewModelScope)
    val top100AnimeTitles =
        topAnimeTitlesPager.flow.map { pagingData -> pagingData.map { it.toAnimeTitle() } }
            .cachedIn(viewModelScope)


}