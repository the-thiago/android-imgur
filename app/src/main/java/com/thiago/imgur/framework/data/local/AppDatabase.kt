package com.thiago.imgur.framework.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import com.thiago.imgur.framework.data.local.dao.ImageDao
import com.thiago.imgur.framework.data.local.dao.RemoteKeyDao
import com.thiago.imgur.framework.data.local.entity.ImageEntity
import com.thiago.imgur.framework.data.local.entity.RemoteKeyEntity

@Database(
    entities = [ImageEntity::class, RemoteKeyEntity::class],
    version = 1,
    exportSchema = false
)
abstract class AppDatabase : RoomDatabase() {

    abstract fun imageDao(): ImageDao

    abstract fun remoteKeyDao(): RemoteKeyDao
}