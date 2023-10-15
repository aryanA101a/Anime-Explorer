package com.aryan.animeexplorer.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import com.aryan.animeexplorer.data.local.dao.AnimeDetailsDao
import com.aryan.animeexplorer.data.local.dao.AnimeTitlesDao
import com.aryan.animeexplorer.data.local.dao.FavouritesDao
import com.aryan.animeexplorer.data.local.dao.PageKeyDao
import com.aryan.animeexplorer.data.local.entity.AnimeDetailsEntity
import com.aryan.animeexplorer.data.local.entity.AnimeTitleEntity
import com.aryan.animeexplorer.data.local.entity.FavouritesEntity
import com.aryan.animeexplorer.data.local.entity.PageKeyEntity

@Database(
    entities = [AnimeTitleEntity::class, PageKeyEntity::class, AnimeDetailsEntity::class, FavouritesEntity::class],
    version = 1
)
abstract class CacheDatabase : RoomDatabase() {
    abstract val animeTitlesPageDao: AnimeTitlesDao
    abstract val pageKeyDao: PageKeyDao
    abstract val animeDetailsDao: AnimeDetailsDao
    abstract val favouritesDao:FavouritesDao
}