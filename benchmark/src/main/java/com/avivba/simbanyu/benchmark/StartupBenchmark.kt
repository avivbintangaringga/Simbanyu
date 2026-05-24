package com.avivba.simbanyu.benchmark

import androidx.benchmark.macro.CompilationMode
import androidx.benchmark.macro.StartupMode
import androidx.benchmark.macro.junit4.MacrobenchmarkRule
import androidx.test.ext.junit.runners.AndroidJUnit4
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class StartupBenchmark {
    @get:Rule
    val benchmarkRule = MacrobenchmarkRule()

    @Test
    fun startupCold() = startup(StartupMode.COLD)

    @Test
    fun startupWarm() = startup(StartupMode.WARM)

    @Test
    fun startupHot() = startup(StartupMode.HOT)

    private fun startup(mode: StartupMode) = benchmarkRule.measureRepeated(
        packageName = "com.avivba.simbanyu",
        metrics = listOf(androidx.benchmark.macro.StartupTimingMetric()),
        iterations = 5,
        startupMode = mode,
        compilationMode = CompilationMode.Partial()
    ) {
        pressHome()
        startActivityAndWait()
    }
}
