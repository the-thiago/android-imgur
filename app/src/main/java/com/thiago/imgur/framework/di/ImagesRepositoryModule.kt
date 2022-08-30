package com.thiago.imgur.framework.di

import com.thiago.core.data.remote.ImgurRemoteDataSource
import com.thiago.core.data.repository.ImgurRepository
import com.thiago.imgur.framework.data.remote.ImgurRemoteDataSourceImpl
import com.thiago.imgur.framework.data.repository.ImgurRepositoryImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
interface ImgurRepositoryModule {

    @Binds
    fun bindImgurRepository(
        repository: ImgurRepositoryImpl
    ): ImgurRepository

    @Binds
    fun bindImgurRemoteDataSource(
        remoteDataSource: ImgurRemoteDataSourceImpl
    ): ImgurRemoteDataSource
}