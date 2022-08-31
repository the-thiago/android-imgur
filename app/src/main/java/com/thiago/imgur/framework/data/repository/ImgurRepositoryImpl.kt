package com.thiago.imgur.framework.data.repository

import androidx.paging.ExperimentalPagingApi
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.PagingSource
import androidx.paging.map
import com.thiago.core.data.remote.ImgurRemoteDataSource
import com.thiago.core.data.repository.ImgurRepository
import com.thiago.core.domain.model.Image
import com.thiago.imgur.framework.data.ImagesPagingSource
import com.thiago.imgur.framework.data.ImagesRemoteMediator
import com.thiago.imgur.framework.data.local.AppDatabase
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class ImgurRepositoryImpl @Inject constructor(
    private val remoteDataSource: ImgurRemoteDataSource,
    private val database: AppDatabase
) : ImgurRepository {

    override fun getImages(): PagingSource<Int, Image> {
        return ImagesPagingSource(remoteDataSource)
    }

    @OptIn(ExperimentalPagingApi::class)
    override fun getCachedImages(pagingConfig: PagingConfig): Flow<PagingData<Image>> {
        return Pager(
            config = pagingConfig,
            remoteMediator = ImagesRemoteMediator(database, remoteDataSource)
        ) {
            database.imageDao().pagingSource()
        }.flow.map { pagingData ->
            pagingData.map { imageEntity ->
                Image(
                    id = imageEntity.id,
                    description = imageEntity.description,
                    url = imageEntity.url,
                    isGif = imageEntity.isGif,
                )
            }
        }
    }
}