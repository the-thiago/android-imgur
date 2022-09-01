package com.thiago.imgur.presentation.images

import androidx.paging.PagingData
import com.nhaarman.mockitokotlin2.any
import com.nhaarman.mockitokotlin2.whenever
import com.thiago.core.domain.model.Image
import com.thiago.core.domain.usecase.GetImagesUseCase
import com.thiago.imgur.MainCoroutineRule
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.runTest
import org.junit.Assert
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.junit.MockitoJUnitRunner

@ExperimentalCoroutinesApi
@RunWith(MockitoJUnitRunner::class)
class ImagesViewModelTest {

    @get:Rule
    var mainCoroutineRule = MainCoroutineRule()

    @Mock
    lateinit var getImageUseCase: GetImagesUseCase

    private lateinit var imagesViewModel: ImagesViewModel

    @Before
    fun setUp() {
        imagesViewModel = ImagesViewModel(getImagesUseCase = getImageUseCase)
    }

    private val pagingDataImages = PagingData.from(
        listOf(
            Image(description = "", url = "", isGif = false),
            Image(description = "", url = "", isGif = false)
        )
    )

    @Test
    fun `should validate the paging data object values when calling imagesPagingData`() {
        runTest {
            whenever(
                getImageUseCase.invoke(any())
            ).thenReturn(
                flowOf(pagingDataImages)
            )

            val result = imagesViewModel.imagesPagingData()
            Assert.assertNotNull(result.first())
        }
    }

    @Test(expected = RuntimeException::class)
    fun `should throw an exception when the calling to the use case returns an exception`() {
        runTest {
            whenever(
                getImageUseCase.invoke(any())
            ).thenThrow(RuntimeException())

            imagesViewModel.imagesPagingData()
        }
    }
}