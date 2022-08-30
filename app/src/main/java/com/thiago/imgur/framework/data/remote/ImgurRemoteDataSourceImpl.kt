package com.thiago.imgur.framework.data.remote

import com.thiago.core.data.remote.ImgurRemoteDataSource
import com.thiago.core.domain.model.Image
import javax.inject.Inject

class ImgurRemoteDataSourceImpl @Inject constructor(
    private val imgurApi: ImgurApi
) : ImgurRemoteDataSource {

    override fun getImages(query: String): Image {
        TODO("Not yet implemented")
    }
}