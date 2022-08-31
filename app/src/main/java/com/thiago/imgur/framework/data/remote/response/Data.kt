package com.thiago.imgur.framework.data.remote.response

import com.google.gson.annotations.SerializedName

data class Data(
    @SerializedName("id")
    val id: String?,
    @SerializedName("images")
    val images: List<ImageResponse>?,
)