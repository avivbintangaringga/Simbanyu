package com.avivba.simbanyu.benchmark

import androidx.benchmark.macro.junit4.BaselineProfileRule
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.uiautomator.By
import androidx.test.uiautomator.Direction
import androidx.test.uiautomator.Until
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class BaselineProfileGenerator {
    @get:Rule
    val baselineProfileRule = BaselineProfileRule()

    @Test
    fun generate() = baselineProfileRule.collect(
        packageName = "com.avivba.simbanyu",
        includeInStartupProfile = true
    ) {
        pressHome()
        startActivityAndWait()

        // Handle login if necessary
        val loginButton = device.wait(Until.findObject(By.res("login_button")), 5000)
        if (loginButton != null) {
            val usernameField = device.findObject(By.res("username_field"))
            val passwordField = device.findObject(By.res("password_field"))
            // Use credentials that are known to work in benchmarks
            usernameField.text = "SW050105"
            passwordField.text = "demo"
            loginButton.click()
            device.wait(Until.hasObject(By.textContains("Penggunaan Air")), 10000)
        }

        // Navigate through main screens
        val usageTab = device.findObject(By.text("Penggunaan"))
        usageTab?.click()
        device.waitForIdle()

        // Scroll usage history list to include it in the profile
        val usageHistoryList = device.wait(Until.findObject(By.res("usage_history_list")), 5000)
        usageHistoryList?.setGestureMargin(device.displayWidth / 5)
        usageHistoryList?.fling(Direction.DOWN)
        device.waitForIdle()

        val billTab = device.findObject(By.text("Tagihan"))
        billTab?.click()
        device.waitForIdle()

        val reportTab = device.findObject(By.text("Lapor"))
        reportTab?.click()
        device.waitForIdle()

        val profileTab = device.findObject(By.text("Profil"))
        profileTab?.click()
        device.waitForIdle()

        // Home Screen and scrolling
        val homeTab = device.findObject(By.text("Home"))
        homeTab?.click()
        device.waitForIdle()
        
        val dashboardScrollable = device.wait(Until.findObject(By.res("dashboard_scrollable")), 5000)
        dashboardScrollable?.setGestureMargin(device.displayWidth / 5)
        dashboardScrollable?.fling(Direction.DOWN)
        device.waitForIdle()
    }
}
