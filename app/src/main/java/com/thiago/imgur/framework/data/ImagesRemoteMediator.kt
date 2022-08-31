package com.thiago.imgur.framework.data

import androidx.paging.ExperimentalPagingApi
import androidx.paging.LoadType
import androidx.paging.PagingState
import androidx.paging.RemoteMediator
import androidx.room.withTransaction
import com.thiago.core.data.remote.ImgurRemoteDataSource
import com.thiago.imgur.framework.data.local.AppDatabase
import com.thiago.imgur.framework.data.local.entity.ImageEntity
import com.thiago.imgur.framework.data.local.entity.RemoteKeyEntity
import retrofit2.HttpException
import java.io.IOException
import javax.inject.Inject

@OptIn(ExperimentalPagingApi::class)
class ImagesRemoteMediator @Inject constructor(
    private val database: AppDatabase,
    private val remoteDataSource: ImgurRemoteDataSource
) : RemoteMediator<Int, ImageEntity>() {

    private val imageDao = database.imageDao()
    private val remoteKeyDao = database.remoteKeyDao()

    @Suppress("ReturnCount")
    override suspend fun load(
        loadType: LoadType,
        state: PagingState<Int, ImageEntity>
    ): MediatorResult {
        return try {
            val pageNumber = when (loadType) {
                LoadType.REFRESH -> 0
                LoadType.PREPEND ->
                    return MediatorResult.Success(endOfPaginationReached = true)
                LoadType.APPEND -> {
                    val remoteKey = database.withTransaction {
                        remoteKeyDao.remoteKey()
                    }

                    if (remoteKey.nexPageNumber == null) {
                        return MediatorResult.Success(endOfPaginationReached = true)
                    }

                    remoteKey.nexPageNumber
                }
            }

            val imagesPaging = remoteDataSource.getImages(pageNumber)
            val nextKey = if (imagesPaging.total > 0) pageNumber + 1 else null

            database.withTransaction {
                if (loadType == LoadType.REFRESH) {
                    remoteKeyDao.clearAll()
                    imageDao.clearAll()
                }

                remoteKeyDao.insertOrReplace(
                    RemoteKeyEntity(nexPageNumber = nextKey)
                )

                val imagesEntities = imagesPaging.images.map {
                    ImageEntity(
                        id = it.id,
                        description = it.description,
                        url = it.url,
                        isGif = it.isGif
                    )
                }

                imageDao.insertAll(imagesEntities)
            }

            MediatorResult.Success(endOfPaginationReached = nextKey == null)
        } catch (e: IOException) {
            MediatorResult.Error(e)
        } catch (e: HttpException) {
            MediatorResult.Error(e)
        }
    }
}