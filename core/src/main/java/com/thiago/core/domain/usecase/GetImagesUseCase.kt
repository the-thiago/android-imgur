package com.thiago.core.domain.usecase

import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.thiago.core.data.repository.ImgurRepository
import com.thiago.core.domain.model.Image
import com.thiago.core.domain.usecase.base.PagingUseCase
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

interface GetImagesUseCase {

    operator fun invoke(params: GetImagesParams): Flow<PagingData<Image>>

    data class GetImagesParams(val pagingConfig: PagingConfig)
}

class GetImagesUseCaseImpl @Inject constructor(
    private val imgurRepository: ImgurRepository
) : PagingUseCase<GetImagesUseCase.GetImagesParams, Image>(), GetImagesUseCase {

    override fun createFlowObservable(
        params: GetImagesUseCase.GetImagesParams
    ): Flow<PagingData<Image>> {
        return imgurRepository.getCachedImages(params.pagingConfig)
    }
}