package com.thiago.core.data.remote

import com.thiago.core.domain.model.ImagePaging

interface ImgurRemoteDataSource {

    suspend fun getImages(page: Int): ImagePaging
}