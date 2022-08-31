package com.thiago.imgur.framework.data.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "remote_keys")
data class RemoteKeyEntity(
    @PrimaryKey
    val id: Int = 0,
    val nexPageNumber: Int?
)