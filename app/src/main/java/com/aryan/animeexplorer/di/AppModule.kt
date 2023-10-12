package com.aryan.animeexplorer.di

import com.apollographql.apollo3.ApolloClient
import com.aryan.animeexplorer.data.ApolloAnimeClient
import com.aryan.animeexplorer.domain.AnimeClient
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

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
}