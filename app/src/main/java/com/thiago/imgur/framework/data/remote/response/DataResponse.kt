package com.thiago.imgur.framework.data.remote.response

import com.google.gson.annotations.SerializedName

data class DataResponse(
    @SerializedName("animated")
    val animated: Boolean,
    @SerializedName("bandwidth")
    val bandwidth: Int,
    @SerializedName("datetime")
    val datetime: Int,
    @SerializedName("deletehash")
    val deleteHash: String,
    @SerializedName("height")
    val height: Int,
    @SerializedName("id")
    val id: String,
    @SerializedName("size")
    val size: Int,
    @SerializedName("type")
    val type: String,
    @SerializedName("views")
    val views: Int,
    @SerializedName("width")
    val width: Int,
    @SerializedName("link")
    val link: String
)