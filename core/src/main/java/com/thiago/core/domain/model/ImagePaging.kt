package com.thiago.core.domain.model

data class ImagePaging(
    val offset: Int,
    val total: Int,
    val images: List<Image>
)