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
            usernameField.text = "testuser"
            passwordField.text = "password123"
            loginButton.click()
            device.wait(Until.hasObject(By.textContains("Penggunaan Air")), 10000)
        }

        // Navigate through main screens
        val usageTab = device.findObject(By.text("Penggunaan"))
        usageTab?.click()
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

        // Scroll some lists
        val homeTab = device.findObject(By.text("Home"))
        homeTab?.click()
        device.waitForIdle()
        
        val scrollable = device.wait(Until.findObject(By.scrollable(true)), 5000)
        scrollable?.fling(Direction.DOWN)
    }
}
