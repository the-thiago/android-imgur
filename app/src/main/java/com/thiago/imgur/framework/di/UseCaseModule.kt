package com.thiago.imgur.framework.di

import com.thiago.core.domain.usecase.GetImagesUseCase
import com.thiago.core.domain.usecase.GetImagesUseCaseImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent

@Module
@InstallIn(ViewModelComponent::class)
interface UseCaseModule {

    @Binds
    fun bindGetImagesUseCase(useCase: GetImagesUseCaseImpl): GetImagesUseCase
}