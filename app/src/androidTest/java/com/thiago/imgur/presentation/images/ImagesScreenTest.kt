package com.thiago.imgur.presentation.images

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.test.assertHasClickAction
import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.assertTextEquals
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onChildren
import androidx.compose.ui.test.onLast
import androidx.compose.ui.test.onNodeWithTag
import androidx.compose.ui.test.performClick
import com.thiago.imgur.R
import com.thiago.imgur.presentation.core.TestTags
import org.junit.Rule
import org.junit.Test

class ImagesScreenTest {

    @get:Rule
    val composeTestRule = createComposeRule()

    @Test
    fun whenHeaderLoadRemoteDataIsShownThenItHasClickAction() {
        composeTestRule.run {
            var text = ""
            setContent {
                text = stringResource(id = R.string.error_refreshing_data)
                var show by remember { mutableStateOf(true) }
                HeaderLoadRemoteData(
                    show = show,
                    onTryClick = { show = !show }
                )
            }
            onNodeWithTag(TestTags.HeaderLoadRemoteData, true)
                .assertIsDisplayed()
                .assertTextEquals(text)
                .performClick()
                .assertDoesNotExist()
        }
    }

    @Test
    fun whenIsLoadingIsTrueThenLoadingIndicatorIsVisible() {
        composeTestRule.run {
            setContent {
                LoadingIndicator(isLoading = true)
            }
            onNodeWithTag(TestTags.LoadingIndicator, true)
                .assertIsDisplayed()
        }
    }

    @Test
    fun whenAppendImagesErrorIsShownThenItHasClickAction() {
        composeTestRule.run {
            var text = ""
            setContent {
                text = stringResource(id = R.string.error_loading_more_try_again)
                var show by remember { mutableStateOf(true) }
                AppendImagesError(showError = show, onTryAgainClick = { show = !show })
            }
            onNodeWithTag(TestTags.AppendImagesError, true)
                .assertIsDisplayed()
                .assertTextEquals(text)
                .performClick()
                .assertDoesNotExist()
        }
    }

    @Test
    fun whenLoadingImagesErrorIsShownThenItHasClickAction() {
        composeTestRule.run {
            setContent {
                LoadingImagesError(onTryAgainClick = {})
            }
            onNodeWithTag(TestTags.LoadingImagesError, true)
                .onChildren()
                .onLast()
                .assertHasClickAction()
        }
    }
}