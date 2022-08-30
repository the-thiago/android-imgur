package com.thiago.imgur.framework.data.remote

import com.thiago.core.domain.model.Image
import retrofit2.http.GET

interface ImgurApi {

    @GET("gallery")
    suspend fun getImages(): Image
}