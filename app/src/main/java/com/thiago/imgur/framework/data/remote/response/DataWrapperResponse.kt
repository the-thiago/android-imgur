package com.thiago.imgur.framework.data.remote.response

data class DataWrapperResponse(
    val data: List<Data>?,
    val status: Int,
    val success: Boolean
)