package com.thiago.imgur.framework.data

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.thiago.core.data.remote.ImgurRemoteDataSource
import com.thiago.core.domain.model.Image

class ImagesPagingSource(
    private val remoteDataSource: ImgurRemoteDataSource
) : PagingSource<Int, Image>() {

    @Suppress("TooGenericExceptionCaught")
    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, Image> {
        return try {
            val pageNumber = params.key ?: 0

            val imagesPaging = remoteDataSource.getImages(pageNumber)

            val prevKey = if (pageNumber > 0) pageNumber - 1 else null
            val nextKey = if (imagesPaging.total > 0) pageNumber + 1 else null

            LoadResult.Page(
                data = imagesPaging.images,
                prevKey = prevKey,
                nextKey = nextKey
            )
        } catch (exception: Exception) {
            LoadResult.Error(exception)
        }
    }

    override fun getRefreshKey(state: PagingState<Int, Image>): Int? {
        return state.anchorPosition?.let {
            state.closestPageToPosition(it)?.prevKey?.plus(1)
                ?: state.closestPageToPosition(it)?.nextKey?.minus(1)
        }
    }
}