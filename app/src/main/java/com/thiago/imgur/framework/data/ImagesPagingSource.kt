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
            val offset = params.key ?: 0

            val queries = hashMapOf(
                "offset" to offset.toString()
            )

//            if (query.isNotEmpty()) {
//                queries["nameStartsWith"] = query
//            }

            val imagesPaging = remoteDataSource.getImages(0)

//            val responseOffset = imagesPaging.offset
//            val totalImages = imagesPaging.total

            LoadResult.Page(
                data = imagesPaging.images,
                prevKey = null,
                nextKey = null
//                if (responseOffset < totalImages) {
//                    responseOffset + LIMIT
//                } else {
//                    null
//                }
            )
        } catch (exception: Exception) {
            LoadResult.Error(exception)
        }
    }

    override fun getRefreshKey(state: PagingState<Int, Image>): Int? {
        return state.anchorPosition?.let { anchorPosition ->
            val anchorPage = state.closestPageToPosition(anchorPosition)
            anchorPage?.prevKey?.plus(LIMIT) ?: anchorPage?.nextKey?.minus(LIMIT)
        }
    }

    companion object {
        private const val LIMIT = 20
    }
}