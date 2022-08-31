package com.thiago.imgur.framework.data.remote.response

import com.google.gson.annotations.SerializedName

data class DataWrapperResponse(
    @SerializedName("data")
    val data: List<Data>?,
    @SerializedName("status")
    val status: Int,
    @SerializedName("success")
    val success: Boolean
)