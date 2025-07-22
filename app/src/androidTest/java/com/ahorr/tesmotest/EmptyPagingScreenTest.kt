package com.ahorr.tesmotest

import androidx.activity.compose.setContent
import androidx.compose.ui.test.junit4.createAndroidComposeRule
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.assertIsDisplayed
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.ahorr.tesmotest.presentation.common.EmptyPagingScreen
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import androidx.paging.LoadState
import java.net.ConnectException
import java.net.SocketTimeoutException

@RunWith(AndroidJUnit4::class)
class EmptyPagingScreenTest {
    @get:Rule
    val composeTestRule = createAndroidComposeRule<MainActivity>()

    @Test
    fun emptyScreen_shows_Internet_UnavailableErrorMessage() {
        val errorMessage = "Server Unavailable."
        val exception = Exception(errorMessage, ConnectException())
        val error = LoadState.Error(exception)
        composeTestRule.activity.setContent {
            EmptyPagingScreen(error = error)
        }
        composeTestRule.onNodeWithText(errorMessage).assertIsDisplayed()
    }

    @Test
    fun emptyScreen_shows_Unchecked_ErrorMessage() {
        val errorMessage = "unchecked"
        val error = LoadState.Error(Exception())
        composeTestRule.activity.setContent {
            EmptyPagingScreen(error = error)
        }
        composeTestRule.onNodeWithText("Unknown Error.").assertIsDisplayed()
    }

    @Test
    fun emptyScreen_shows_SocketErrorMessage() {


        val errorMessage = "Server Unavailable."
        val exception = Exception(errorMessage, SocketTimeoutException())
        val error = LoadState.Error(exception)


        composeTestRule.activity.setContent {
            EmptyPagingScreen(error = error)
        }
        composeTestRule.onNodeWithText("Server Unavailable.").assertIsDisplayed()
    }
} 