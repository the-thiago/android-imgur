package com.thiago.imgur.framework.di

import android.content.Context
import androidx.room.Room
import com.thiago.imgur.framework.data.local.AppDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
object DatabaseModule {

    @Provides
    fun provideAppDatabase(
        @ApplicationContext context: Context
    ): AppDatabase = Room.databaseBuilder(
        context,
        AppDatabase::class.java,
        "database"
    ).build()

    @Provides
    fun provideImageDao(appDatabase: AppDatabase) = appDatabase.imageDao()
}