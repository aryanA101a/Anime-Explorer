package com.aryan.animeexplorer.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import com.aryan.animeexplorer.data.local.dao.AnimeTitlesPageDao
import com.aryan.animeexplorer.data.local.dao.PageKeyDao
import com.aryan.animeexplorer.data.local.entity.AnimeTitleEntity
import com.aryan.animeexplorer.data.local.entity.PageKeyEntity

@Database(
    entities = [AnimeTitleEntity::class,PageKeyEntity::class],
    version = 1
)
abstract class CacheDatabase : RoomDatabase() {
    abstract val animeTitlesPageDao: AnimeTitlesPageDao
    abstract val pageKeyDao: PageKeyDao
}