package com.thiago.imgur.framework.data.remote.response

import com.google.gson.annotations.SerializedName
import com.thiago.core.domain.model.Image

data class ImageResponse(
    @SerializedName("description")
    val description: String?,
    @SerializedName("link")
    val url: String?,
    @SerializedName("type")
    val type: String?,
)

fun ImageResponse.toImage(): Image {
    return Image(
        description = this.description ?: "",
        url = this.url ?: "",
        isGif = this.type == "image/gif"
    )
}