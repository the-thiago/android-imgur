package com.thiago.imgur.framework.data.local.dao

import androidx.paging.PagingSource
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.thiago.imgur.framework.data.local.entity.ImageEntity

@Dao
interface ImageDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(images: List<ImageEntity>)

    @Query("SELECT * FROM images")
    fun pagingSource(): PagingSource<Int, ImageEntity>

    @Query("DELETE FROM images")
    suspend fun clearAll()
}