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
class UsageHistoryBenchmark {
    @get:Rule
    val benchmarkRule = MacrobenchmarkRule()

    @Test
    fun scrollUsageHistory() = benchmarkRule.measureRepeated(
        packageName = "com.avivba.simbanyu",
        metrics = listOf(FrameTimingMetric()),
        iterations = 5,
        startupMode = StartupMode.WARM,
        compilationMode = CompilationMode.None(),
        setupBlock = {
            pressHome()
            startActivityAndWait()
            
            // Handle login
            val loginButton = device.wait(Until.findObject(By.res("login_button")), 5000)
            if (loginButton != null) {
                device.findObject(By.res("username_field")).text = "testuser"
                device.findObject(By.res("password_field")).text = "password123"
                loginButton.click()
                device.wait(Until.hasObject(By.textContains("Penggunaan Air")), 10000)
            }

            // Navigate to Usage History
            val usageTab = device.findObject(By.text("Penggunaan"))
            usageTab?.click()
            // Wait for the chart or some text in Usage History to appear
            device.wait(Until.hasObject(By.res("usage_history_list")), 5000)
        }
    ) {
        val scrollable = device.wait(Until.findObject(By.res("usage_history_list")), 5000)
        if (scrollable != null) {
            scrollable.setGestureMargin(device.displayWidth / 5)
            scrollable.fling(Direction.DOWN)
            device.waitForIdle()
            scrollable.fling(Direction.UP)
        }
    }
}
