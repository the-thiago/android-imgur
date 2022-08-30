package com.thiago.imgur.framework.data.repository

import androidx.paging.PagingSource
import com.thiago.core.data.remote.ImgurRemoteDataSource
import com.thiago.core.data.repository.ImgurRepository
import com.thiago.core.domain.model.Image
import javax.inject.Inject

class ImgurRepositoryImpl @Inject constructor(
    private val remoteDataSource: ImgurRemoteDataSource
) : ImgurRepository {

    override fun getImages(): PagingSource<Int, Image> {
        TODO("Not yet implemented")
    }
}