package com.thiago.imgur.framework.data.remote.response

data class Image(
    val ad_type: Int,
    val animated: Boolean,
    val description: String,
    val height: Int,
    val id: String,
    val link: String?,
    val title: Any,
    val type: String,
    val views: Int,
    val width: Int
)