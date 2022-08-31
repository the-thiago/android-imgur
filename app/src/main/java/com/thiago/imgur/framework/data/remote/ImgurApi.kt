package com.thiago.imgur.framework.data.remote

import com.thiago.imgur.framework.data.remote.response.DataWrapperResponse
import retrofit2.http.GET
import retrofit2.http.Query

interface ImgurApi {

    @GET("gallery/search/?q=cats")
    suspend fun getImages(@Query("page") page: Int): DataWrapperResponse
}