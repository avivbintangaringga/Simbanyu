package com.avivba.simbanyu.ui.components.topbar

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.dropShadow
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.graphics.shadow.Shadow
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.avivba.simbanyu.R
import com.avivba.simbanyu.ui.components.text.SimbanyuText
import com.avivba.simbanyu.ui.theme.SimbanyuTheme

@OptIn(ExperimentalMaterial3Api::class)
@Preview
@Composable
fun TopBar(
    modifier: Modifier = Modifier,
    text: String = "Simbanyu",
    navigationIcon: @Composable () -> Unit = {
//        Icon(
//            modifier = Modifier
//                .padding(horizontal = SimbanyuTheme.padding.large),
//            painter = painterResource(R.drawable.logo_v2),
//            contentDescription = null,
////            tint = SimbanyuTheme.colors.brand
//        )


        Image(
            painter = painterResource(R.drawable.logo_v2),
            contentDescription = "Logo",
            modifier = Modifier
                .height(28.dp)
                .padding(
                    horizontal = SimbanyuTheme.padding.large
                )
        )
    },
    actions: @Composable RowScope.() -> Unit = {}
) {
    TopAppBar(
        modifier = modifier
            .height(IntrinsicSize.Min)
            .dropShadow(
                shape = RectangleShape,
                shadow = Shadow(
                    radius = 4.dp,
                    alpha = 0.2f
                )
            ),
        navigationIcon = navigationIcon,
        title = {
            SimbanyuText(
                text = text,
                style = SimbanyuTheme.typography.displayMedium,
                color = SimbanyuTheme.colors.brand
            )
        },
        actions = actions
    )
}