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
import com.aryan.animeexplorer.data.remote.ApolloAnimeClient.Companion.pageSize
import com.aryan.animeexplorer.data.repository.AnimeDetailsRepositoryImpl
import com.aryan.animeexplorer.domain.AnimeClient
import com.aryan.animeexplorer.domain.model.AnimeTitleType
import com.aryan.animeexplorer.domain.repository.AnimeDetailsRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Qualifier
import javax.inject.Singleton

@Qualifier
@Retention(AnnotationRetention.BINARY)
annotation class TrendingAnimeTitlesPager

@Qualifier
@Retention(AnnotationRetention.BINARY)
annotation class PopularAnimeTitlesPager

@Qualifier
@Retention(AnnotationRetention.BINARY)
annotation class Top100AnimeTitlesPager


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
    @TrendingAnimeTitlesPager
    fun provideTrendingAnimeTitlesPager(
        cacheDatabase: CacheDatabase,
        animeClient: AnimeClient
    ): Pager<Int, AnimeTitleEntity> {
        return Pager(
            config = PagingConfig(pageSize = pageSize),
            remoteMediator = AnimeTitleRemoteMediator(
                cacheDB = cacheDatabase,
                animeClient = animeClient,
                type = AnimeTitleType.TRENDING
            )
        ) {
            cacheDatabase.animeTitlesPageDao.pagingSource(AnimeTitleType.TRENDING)
        }
    }

    @Provides
    @Singleton
    @PopularAnimeTitlesPager
    fun providePopularAnimeTitlesPager(
        cacheDatabase: CacheDatabase,
        animeClient: AnimeClient
    ): Pager<Int, AnimeTitleEntity> {
        return Pager(
            config = PagingConfig(pageSize = pageSize),
            remoteMediator = AnimeTitleRemoteMediator(
                cacheDB = cacheDatabase,
                animeClient = animeClient,
                type = AnimeTitleType.POPULAR
            )
        ) {
            cacheDatabase.animeTitlesPageDao.pagingSource(AnimeTitleType.POPULAR)
        }
    }

    @Provides
    @Singleton
    @Top100AnimeTitlesPager
    fun provideTop100AnimeTitlesPager(
        cacheDatabase: CacheDatabase,
        animeClient: AnimeClient
    ): Pager<Int, AnimeTitleEntity> {
        return Pager(
            config = PagingConfig(pageSize = pageSize),
            remoteMediator = AnimeTitleRemoteMediator(
                cacheDB = cacheDatabase,
                animeClient = animeClient,
                type = AnimeTitleType.TOP100
            )
        ) {
            cacheDatabase.animeTitlesPageDao.pagingSource(AnimeTitleType.TOP100)
        }
    }

    @Provides
    @Singleton
    fun provideAnimeDetailsRepository(
        animeClient: AnimeClient,
        cacheDatabase: CacheDatabase
    ): AnimeDetailsRepository {
        return AnimeDetailsRepositoryImpl(animeClient, cacheDatabase)
    }


}