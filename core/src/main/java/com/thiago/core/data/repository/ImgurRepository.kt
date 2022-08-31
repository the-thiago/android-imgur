package com.thiago.core.data.repository

import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.PagingSource
import com.thiago.core.domain.model.Image
import kotlinx.coroutines.flow.Flow

interface ImgurRepository {

    fun getImages(): PagingSource<Int, Image>

    fun getCachedImages(pagingConfig: PagingConfig): Flow<PagingData<Image>>
}