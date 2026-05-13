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
        compilationMode = CompilationMode.None()
    ) {
        startActivityAndWait()
        
        // Wait for dashboard to load (assuming it lands on login or dashboard)
        // If it lands on login, we might need to bypass it or use a pre-authenticated state
        // For now, let's assume we want to measure scrolling on the main screen if it's there
        
        val scrollable = device.wait(Until.findObject(By.scrollable(true)), 5000)
        scrollable?.setGestureMargin(device.displayWidth / 5)
        scrollable?.fling(Direction.DOWN)
    }
}
