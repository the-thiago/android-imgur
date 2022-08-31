package com.thiago.core.domain.model

data class Image(
    val id: String,
    val description: String,
    val url: String,
    val isGif: Boolean
)