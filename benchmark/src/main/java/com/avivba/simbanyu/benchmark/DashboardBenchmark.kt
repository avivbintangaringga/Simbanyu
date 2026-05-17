package com.avivba.simbanyu.benchmark

import androidx.benchmark.macro.CompilationMode
import androidx.benchmark.macro.FrameTimingMetric
import androidx.benchmark.macro.StartupMode
import androidx.benchmark.macro.junit4.MacrobenchmarkRule
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.uiautomator.By
import androidx.test.uiautomator.Direction
import androidx.test.uiautomator.Until
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class DashboardBenchmark {
    @get:Rule
    val benchmarkRule = MacrobenchmarkRule()

    @Test
    fun scrollDashboard() = benchmarkRule.measureRepeated(
        packageName = "com.avivba.simbanyu",
        metrics = listOf(FrameTimingMetric()),
        iterations = 5,
        startupMode = StartupMode.WARM,
        compilationMode = CompilationMode.None(),
        setupBlock = {
            pressHome()
            startActivityAndWait()
            
            // Check if login is needed. Wait for a few seconds for either dashboard or login
            val loginButton = device.wait(Until.findObject(By.res("login_button")), 5000)
            if (loginButton != null) {
                val usernameField = device.findObject(By.res("username_field"))
                val passwordField = device.findObject(By.res("password_field"))
                
                // NOTE: Replace these with valid test credentials for your environment
                usernameField.text = "testuser"
                passwordField.text = "password123"
                
                loginButton.click()
                
                // Wait for dashboard content to appear
                device.wait(Until.hasObject(By.textContains("Penggunaan Air")), 10000)
            }
        }
    ) {
        val scrollable = device.wait(Until.findObject(By.scrollable(true)), 5000)
        if (scrollable != null) {
            scrollable.setGestureMargin(device.displayWidth / 5)
            scrollable.fling(Direction.DOWN)
        }
    }
}
