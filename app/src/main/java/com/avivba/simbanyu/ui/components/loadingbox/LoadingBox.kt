package com.avivba.simbanyu.ui.components.loadingbox

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.EnterTransition
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.avivba.simbanyu.ui.theme.SimbanyuTheme

@Composable
fun LoadingBox(
    modifier: Modifier = Modifier,
    isLoading: Boolean,
    content: @Composable () -> Unit
) {
    Box(modifier = modifier.fillMaxSize()) {
        content()

        AnimatedVisibility(
            visible = isLoading,
            enter = EnterTransition.None,
            exit = fadeOut(animationSpec = tween(durationMillis = 300)),
            modifier = Modifier.fillMaxSize()
        ) {
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .background(SimbanyuTheme.colors.screenBackground),
                contentAlignment = Alignment.Center
            ) {
                CircularProgressIndicator(
                    color = SimbanyuTheme.colors.brand
                )
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun LoadingBoxPreview() {
    SimbanyuTheme {
        LoadingBox(isLoading = true) {
            // Preview content
        }
    }
}
