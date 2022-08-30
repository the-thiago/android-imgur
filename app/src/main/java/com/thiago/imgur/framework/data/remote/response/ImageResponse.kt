package com.thiago.imgur.framework.data.remote.response

import com.google.gson.annotations.SerializedName
import com.thiago.core.domain.model.Image

data class ImageResponse(
    @SerializedName("data")
    val data: DataResponse,
    @SerializedName("status")
    val status: Int,
    @SerializedName("success")
    val success: Boolean
)

fun ImageResponse.toImageModel(): Image {
    return Image(url = this.data.link)
}