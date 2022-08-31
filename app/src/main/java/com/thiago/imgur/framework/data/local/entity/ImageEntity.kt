package com.thiago.imgur.framework.data.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "images")
data class ImageEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    val description: String = "",
    val url: String = "",
    val isGif: Boolean = false
)