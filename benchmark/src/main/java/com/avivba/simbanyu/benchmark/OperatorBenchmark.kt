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
class OperatorBenchmark {
    @get:Rule
    val benchmarkRule = MacrobenchmarkRule()

    @Test
    fun scrollSubscribers() = benchmarkRule.measureRepeated(
        packageName = "com.avivba.simbanyu",
        metrics = listOf(FrameTimingMetric()),
        iterations = 5,
        startupMode = StartupMode.WARM,
        compilationMode = CompilationMode.Partial(),
        setupBlock = {
            pressHome()
            startActivityAndWait()
            
            // Handle login - assuming operator login for this test
            // You might need different credentials for operator
            val loginButton = device.wait(Until.findObject(By.res("login_button")), 5000)
            if (loginButton != null) {
                device.findObject(By.res("username_field")).text = "operator_user"
                device.findObject(By.res("password_field")).text = "operator_pass"
                loginButton.click()
                device.wait(Until.hasObject(By.textContains("Simbanyu Operator")), 10000)
            }

            // Navigate to Subscribers
            val subscribersTab = device.findObject(By.text("Pelanggan"))
            subscribersTab?.click()
            device.wait(Until.hasObject(By.res("subscriber_list")), 5000)
        }
    ) {
        val scrollable = device.wait(Until.findObject(By.scrollable(true)), 5000)
        if (scrollable != null) {
            scrollable.setGestureMargin(device.displayWidth / 5)
            scrollable.fling(Direction.DOWN)
            device.waitForIdle()
            scrollable.fling(Direction.UP)
        }
    }

    @Test
    fun interactWithMap() = benchmarkRule.measureRepeated(
        packageName = "com.avivba.simbanyu",
        metrics = listOf(FrameTimingMetric()),
        iterations = 5,
        startupMode = StartupMode.WARM,
        compilationMode = CompilationMode.Partial(),
        setupBlock = {
            pressHome()
            startActivityAndWait()
            
            // Handle login
            val loginButton = device.wait(Until.findObject(By.res("login_button")), 5000)
            if (loginButton != null) {
                device.findObject(By.res("username_field")).text = "operator_user"
                device.findObject(By.res("password_field")).text = "operator_pass"
                loginButton.click()
                device.wait(Until.hasObject(By.textContains("Simbanyu Operator")), 10000)
            }

            // Navigate to Map
            val mapTab = device.findObject(By.text("Peta"))
            mapTab?.click()
            device.wait(Until.hasObject(By.descContains("Google Map")), 10000)
        }
    ) {
        // Panning the map
        device.swipe(
            device.displayWidth / 2,
            device.displayHeight / 2,
            device.displayWidth / 4,
            device.displayHeight / 4,
            20
        )
        device.waitForIdle()
    }
}
