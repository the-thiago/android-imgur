package com.thiago.imgur.framework.data.remote

import com.thiago.core.data.remote.ImgurRemoteDataSource
import com.thiago.core.domain.model.Image
import com.thiago.core.domain.model.ImagePaging
import javax.inject.Inject

class ImgurRemoteDataSourceImpl @Inject constructor(
    private val imgurApi: ImgurApi
) : ImgurRemoteDataSource {

    override suspend fun getImages(page: Int): ImagePaging {
        val response = imgurApi.getImages(page)
        val images = mutableListOf<Image>()
        response.data?.forEach { data ->
            data.images?.forEach { image ->
                if (image.link?.isNotBlank() == true &&
                    (image.type.contains("jpeg") || image.type.contains("png"))
                ) {
                    images.add(Image(url = image.link))
                }
            }
        }
        return ImagePaging(total = images.size, images = images)
    }
}