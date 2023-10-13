package com.aryan.animeexplorer.di

import android.content.Context
import androidx.paging.ExperimentalPagingApi
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.room.Room
import com.apollographql.apollo3.ApolloClient
import com.aryan.animeexplorer.data.local.entity.AnimeTitleEntity
import com.aryan.animeexplorer.data.local.CacheDatabase
import com.aryan.animeexplorer.data.remote.AnimeTitleRemoteMediator
import com.aryan.animeexplorer.data.remote.ApolloAnimeClient
import com.aryan.animeexplorer.domain.AnimeClient
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@OptIn(ExperimentalPagingApi::class)
@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    @Singleton
    fun provideApolloClient(): ApolloClient {
        return ApolloClient.Builder().serverUrl("https://graphql.anilist.co").build()
    }

    @Provides
    @Singleton
    fun providesAnimeClient(apolloClient: ApolloClient): AnimeClient {
        return ApolloAnimeClient(apolloClient)
    }

    @Provides
    @Singleton
    fun provideCacheDatabase(@ApplicationContext context: Context): CacheDatabase {
        return Room.databaseBuilder(context, CacheDatabase::class.java, "cache.db").build()
    }

    @Provides
    @Singleton
    fun providesAnimePager(cacheDatabase: CacheDatabase,animeClient: AnimeClient): Pager<Int, AnimeTitleEntity>{
        return Pager(
            config = PagingConfig(pageSize = 16),
            remoteMediator=AnimeTitleRemoteMediator(
                cacheDB = cacheDatabase,
                animeClient = animeClient
            )
        ){
         cacheDatabase.animeTitlesPageDao.pagingSource()
        }
    }
}