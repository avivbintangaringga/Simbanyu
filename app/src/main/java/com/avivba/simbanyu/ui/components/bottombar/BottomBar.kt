package com.avivba.simbanyu.ui.components.bottombar

import androidx.compose.foundation.background
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemColors
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.dropShadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.shadow.Shadow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.avivba.simbanyu.ui.theme.SimbanyuTheme

@Preview
@Composable
fun BottomBar(
    modifier: Modifier = Modifier
) {
    NavigationBar(
        modifier = modifier
            .dropShadow(
                shape = SimbanyuTheme.shapes.bottomBar,
                shadow = Shadow(
                    radius = 8.dp,
                    alpha = 0.2f
                )
            )
            .background(
                color = SimbanyuTheme.colors.cardBackground,
                shape = SimbanyuTheme.shapes.bottomBar
            ),
        containerColor = Color.Transparent,
        contentColor = Color.Transparent,
    ) {
    }
}