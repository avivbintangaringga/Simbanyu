package com.avivba.simbanyu.ui.components.background

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.avivba.simbanyu.ui.theme.SimbanyuTheme

@Composable
fun Background(
    modifier: Modifier = Modifier,
) {
    Spacer(
        modifier = modifier.fillMaxSize()
            .background(
                SimbanyuTheme.colors.screenBackground
            )
    )
}

@Preview(showBackground = true)
@Composable
private fun BackgroundPreview() {
    Background()
}