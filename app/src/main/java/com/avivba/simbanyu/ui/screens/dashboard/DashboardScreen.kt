package com.avivba.simbanyu.ui.screens.dashboard

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.avivba.simbanyu.ui.components.text.SimbanyuText

@Composable
fun DashboardScreen(
    modifier: Modifier = Modifier
) {
    Scaffold(
        modifier = modifier
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .padding(paddingValues)
        ) {
            SimbanyuText(
                "DASHBOARD"
            )
        }
    }
}

@Preview
@Composable
fun DashboardPreview() {
    DashboardScreen()
}