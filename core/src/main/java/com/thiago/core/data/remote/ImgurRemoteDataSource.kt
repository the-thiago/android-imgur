package com.thiago.core.data.remote

import com.thiago.core.domain.model.Image

interface ImgurRemoteDataSource {

    fun getImages(query: String): Image
}