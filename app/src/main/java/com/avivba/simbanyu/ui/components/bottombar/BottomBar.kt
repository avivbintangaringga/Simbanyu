package com.avivba.simbanyu.ui.components.bottombar

import androidx.compose.foundation.background
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemColors
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.dropShadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.shadow.Shadow
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.avivba.simbanyu.ui.components.text.SimbanyuText
import com.avivba.simbanyu.ui.shared.Screen
import com.avivba.simbanyu.ui.theme.SimbanyuTheme
data class BottomBarMenuItem(
    val title: String,
    val iconId: Int,
    val screen: Screen
)

@Preview
@Composable
fun BottomBar(
    modifier: Modifier = Modifier,
    selected: Screen = Screen.MainScreen.Home,
    menus: List<BottomBarMenuItem> = emptyList(),
    onSelectedMenuChanged: (BottomBarMenuItem) -> Unit = {}
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
                color = SimbanyuTheme.colors.surfaceCard,
                shape = SimbanyuTheme.shapes.bottomBar
            ),
        containerColor = Color.Transparent,
        contentColor = Color.Transparent,
    ) {
        menus.forEach { item ->
            NavigationBarItem(
                selected = item.screen == selected,
                icon = {
                    Icon(
                        painter = painterResource(item.iconId),
                        contentDescription = item.title,
                    )
                },
                label = {
                    SimbanyuText(
                        text = item.title,
                        style = SimbanyuTheme.typography.bodySmall
                    )
                },
                onClick = {
                    onSelectedMenuChanged(item)
                },
                colors = NavigationBarItemColors(
                    selectedIconColor = SimbanyuTheme.colors.brandText,
                    selectedTextColor = SimbanyuTheme.colors.brandText,
                    selectedIndicatorColor = SimbanyuTheme.colors.brand,
                    unselectedIconColor = SimbanyuTheme.colors.brand,
                    unselectedTextColor = SimbanyuTheme.colors.brand,
                    disabledIconColor = SimbanyuTheme.colors.brand,
                    disabledTextColor = SimbanyuTheme.colors.brand,
                )
            )
        }
    }
}