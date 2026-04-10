package com.avivba.simbanyu.ui.screens.splash

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp

@Composable
fun SplashScreen(modifier: Modifier = Modifier) {
    Box(
        modifier = modifier.background(Color.White)
    ) {
        Text("HEELLOO", modifier = modifier)
    }
}

@Preview
@Composable
fun SplashScreenPreview() {
    SplashScreen(Modifier.padding(10.dp))
}