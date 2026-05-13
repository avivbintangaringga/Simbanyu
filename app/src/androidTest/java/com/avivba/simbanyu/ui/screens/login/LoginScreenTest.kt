package com.avivba.simbanyu.ui.screens.login

import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.junit4.v2.createComposeRule
import androidx.compose.ui.test.onNodeWithText
import com.avivba.simbanyu.ui.theme.SimbanyuTheme
import org.junit.Rule
import org.junit.Test

class LoginScreenTest {

    @get:Rule
    val composeTestRule = createComposeRule()

    @Test
    fun loginScreen_displaysWelcomeMessage() {
        composeTestRule.setContent {
            SimbanyuTheme {
                LoginContent(
                    state = LoginUiState(),
                    onAction = {}
                )
            }
        }

        composeTestRule.onNodeWithText("Selamat Datang").assertIsDisplayed()
        composeTestRule.onNodeWithText("Masuk untuk mengelola layanan air anda").assertIsDisplayed()
    }

    @Test
    fun loginScreen_displaysInputFields() {
        composeTestRule.setContent {
            SimbanyuTheme {
                LoginContent(
                    state = LoginUiState(),
                    onAction = {}
                )
            }
        }

        composeTestRule.onNodeWithText("USERNAME / NO. HP / NO. METER").assertIsDisplayed()
        composeTestRule.onNodeWithText("PASSWORD").assertIsDisplayed()
    }
}
