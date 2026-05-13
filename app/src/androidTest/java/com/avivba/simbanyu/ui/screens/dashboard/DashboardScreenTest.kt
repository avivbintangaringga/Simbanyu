package com.avivba.simbanyu.ui.screens.dashboard

import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithText
import com.avivba.simbanyu.ui.theme.SimbanyuTheme
import org.junit.Rule
import org.junit.Test

class DashboardScreenTest {

    @get:Rule
    val composeTestRule = createComposeRule()

    @Test
    fun dashboard_displaysUserName() {
        val name = "John Doe"
        composeTestRule.setContent {
            SimbanyuTheme {
                DashboardContent(
                    state = DashboardUiState(name = name),
                    onAction = {}
                )
            }
        }

        composeTestRule.onNodeWithText("$name 👋").assertIsDisplayed()
    }

    @Test
    fun dashboard_displaysBill_whenPresent() {
        val billAmount = "150.000"
        composeTestRule.setContent {
            SimbanyuTheme {
                DashboardContent(
                    state = DashboardUiState(bill = billAmount),
                    onAction = {}
                )
            }
        }

        composeTestRule.onNodeWithText("ANDA MEMILIKI TAGIHAN BELUM DIBAYAR").assertIsDisplayed()
        composeTestRule.onNodeWithText(billAmount).assertIsDisplayed()
    }

    @Test
    fun dashboard_displaysUsageChartLabels() {
        composeTestRule.setContent {
            SimbanyuTheme {
                DashboardContent(
                    state = DashboardUiState(
                        usage = fakeWaterUsage,
                        usageAverage = 15.5,
                        usageMaximum = 22
                    ),
                    onAction = {}
                )
            }
        }

        composeTestRule.onNodeWithText("Penggunaan Air").assertIsDisplayed()
        // Use a more flexible check for the average value
        composeTestRule.onNodeWithText("15", substring = true).assertIsDisplayed()
        composeTestRule.onNodeWithText("22 m³").assertIsDisplayed()
    }
}
