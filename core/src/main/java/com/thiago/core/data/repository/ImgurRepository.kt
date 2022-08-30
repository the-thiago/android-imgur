package com.thiago.core.data.repository

import androidx.paging.PagingSource
import com.thiago.core.domain.model.Image

interface ImgurRepository {

    fun getImages(): PagingSource<Int, Image>
}