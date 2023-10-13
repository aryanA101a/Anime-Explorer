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
import java.io.InvalidObjectException
import javax.inject.Inject

class AnimeTitleRemoteMediator @Inject constructor(
    private val cacheDB: CacheDatabase,
    private val animeClient: AnimeClient
) : RemoteMediator<Int, AnimeTitleEntity>() {

    @OptIn(ExperimentalPagingApi::class)
    override suspend fun load(
        loadType: LoadType,
        state: PagingState<Int, AnimeTitleEntity>
    ): MediatorResult {
        Log.i("LOAD", "load: Start")
        return try {
            val loadKey = when (loadType) {
                LoadType.REFRESH -> 1
                LoadType.PREPEND -> return MediatorResult.Success(endOfPaginationReached = true)
                LoadType.APPEND -> {
                    val lastItem = state.lastItemOrNull()
                    if (lastItem == null) {
                        Log.i("loadkey", "loadkey:1 ")
                        1
                    } else {
                        val currentPage = cacheDB.pageKeyDao.pageKey(lastItem.id).currentPage
                            ?: throw InvalidObjectException("Remote key should not be null for $loadType")
                        currentPage + 1
                    }
                }
            }
            val animeTitleResults =
                animeClient.getAnimeTitleResults(page = loadKey, perPage = state.config.pageSize)

            Log.i("LOAD", "load: page:$loadKey $loadType size:${animeTitleResults.size}")

            cacheDB.withTransaction {
                if (loadType == LoadType.REFRESH) {
                    cacheDB.pageKeyDao.clearAll()
                    cacheDB.animeTitlesPageDao.clearAll()
                }

                val pageKeys = animeTitleResults.map {
                    PageKeyEntity(
                        animeTitleID = it.id,
                        currentPage = it.currentPage,
                        hasNextPage = it.hasNextPage
                    )
                }
                val animeTitleEntities =
                    animeTitleResults.map { it.toAnimeTitleEntity() }

                cacheDB.pageKeyDao.upsertAll(pageKeys)
                cacheDB.animeTitlesPageDao.upsertAll(animeTitleEntities)

            }
            val hasNext = cacheDB.pageKeyDao.pageKey(animeTitleResults.last().id).hasNextPage
                ?: throw InvalidObjectException("Has next should not be null")
            MediatorResult.Success(endOfPaginationReached = !hasNext)
        } catch (e: Exception) {
            Log.e("MediatorError", "load: $e")
            MediatorResult.Error(e)
        }
    }


}
