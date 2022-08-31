package com.thiago.imgur.framework.data.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "images")
data class ImageEntity(
    @PrimaryKey(autoGenerate = false)
    val id: String = "",
    val description: String = "",
    val url: String = "",
    val isGif: Boolean = false
)