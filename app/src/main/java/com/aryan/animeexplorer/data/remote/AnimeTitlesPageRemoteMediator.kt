@file:OptIn(ExperimentalPagingApi::class)

package com.aryan.animeexplorer.data.remote

import android.util.Log
import androidx.paging.ExperimentalPagingApi
import androidx.paging.LoadType
import androidx.paging.PagingState
import androidx.paging.RemoteMediator
import androidx.room.withTransaction
import com.aryan.animeexplorer.data.local.entity.AnimeTitleEntity
import com.aryan.animeexplorer.data.local.CacheDatabase
import com.aryan.animeexplorer.data.local.entity.PageKeyEntity
import com.aryan.animeexplorer.data.mappers.toAnimeTitleEntity
import com.aryan.animeexplorer.domain.AnimeClient
import com.aryan.animeexplorer.domain.model.AnimeTitleType
import com.aryan.type.MediaSort
import java.io.InvalidObjectException

class AnimeTitleRemoteMediator(
    private val type: AnimeTitleType,
    private val cacheDB: CacheDatabase,
    private val animeClient: AnimeClient
) : RemoteMediator<Int, AnimeTitleEntity>() {

    @OptIn(ExperimentalPagingApi::class)
    override suspend fun load(
        loadType: LoadType,
        state: PagingState<Int, AnimeTitleEntity>
    ): MediatorResult {
        Log.i("LOAD", "load: Start $type")
        return try {
            var hasNext: Boolean?
            val loadKey = when (loadType) {
                LoadType.REFRESH -> 1
                LoadType.PREPEND -> return MediatorResult.Success(endOfPaginationReached = true)
                LoadType.APPEND -> {
                    val lastItem = state.lastItemOrNull()
                    if (lastItem == null) {
                        1
                    } else {
                        val pageKey = cacheDB.pageKeyDao.pageKey(lastItem.id, type)
                        hasNext = pageKey?.hasNextPage
                        if (pageKey?.currentPage != null)
                            pageKey.currentPage + 1
                        else
                            throw InvalidObjectException("Remote key should not be null for $loadType")
                    }
                }
            }
            val animeTitlesResult = animeClient.getAnimeTitles(
                page = loadKey,
                perPage = state.config.pageSize,
                sort = when (type) {
                    AnimeTitleType.TRENDING -> MediaSort.TRENDING_DESC
                    AnimeTitleType.POPULAR -> MediaSort.POPULARITY_DESC
                    AnimeTitleType.TOP100 -> MediaSort.SCORE_DESC
                }
            )


            Log.i(
                "LOAD",
                "$type: page:$loadKey $loadType size:${animeTitlesResult?.animeTitles?.size}"
            )

            animeTitlesResult?.let {
                cacheDB.withTransaction {
                    if (loadType == LoadType.REFRESH) {
                        cacheDB.pageKeyDao.clearAll()
                        cacheDB.animeTitlesPageDao.clearAll()
                    }

                    val pageKeys = animeTitlesResult.animeTitles.map {
                        PageKeyEntity(
                            animeTitleID = it.id,
                            currentPage = animeTitlesResult.currentPage,
                            hasNextPage = animeTitlesResult.hasNextPage,
                            type = type
                        )
                    }
                    val animeTitleEntities =
                        animeTitlesResult.animeTitles.map { it.toAnimeTitleEntity(type) }

                    cacheDB.pageKeyDao.upsertAll(pageKeys)
                    cacheDB.animeTitlesPageDao.upsertAll(animeTitleEntities)

                }
            }
            hasNext = animeTitlesResult?.hasNextPage

            hasNext ?: throw InvalidObjectException("hasNext should not be null")

            MediatorResult.Success(endOfPaginationReached = !hasNext)
        } catch (e: Exception) {
            Log.e("MediatorError", "load: $e")
            MediatorResult.Error(e)
        }
    }


}
