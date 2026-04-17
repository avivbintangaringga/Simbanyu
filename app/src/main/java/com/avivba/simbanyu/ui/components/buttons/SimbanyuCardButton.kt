package com.avivba.simbanyu.ui.components.buttons

import androidx.annotation.DrawableRes
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Icon
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.avivba.simbanyu.ui.components.text.SimbanyuText
import com.avivba.simbanyu.ui.theme.SimbanyuTheme

@Composable
fun SimbanyuCardButton(
    modifier: Modifier = Modifier,
    title: String,
    subTitle: String? = null,
    @DrawableRes iconId: Int? = null,
    color: Color = SimbanyuTheme.colors.textBody,
    cardColor: Color = SimbanyuTheme.colors.surfaceCardAlternate,
    shape: Shape = SimbanyuTheme.shapes.large,
    onClick: () -> Unit = {},
) {
    Surface(
        onClick = onClick,
        modifier = modifier,
        color = cardColor,
        shape = shape,
    ) {
        Column(
            modifier = Modifier
                .padding(SimbanyuTheme.padding.large),
        ) {
            if (iconId != null) {
                Icon(
                    modifier = Modifier
                        .size(32.dp),
                    painter = painterResource(iconId),
                    contentDescription = null,
                    tint = color
                )
            }
            SimbanyuText(
                text = title,
                modifier = Modifier.weight(1f),
                style = SimbanyuTheme.typography.displaySmall,
                color = color
            )
            if (subTitle != null) {
                SimbanyuText(
                    text = subTitle,
                    modifier = Modifier.weight(1f),
                    style = SimbanyuTheme.typography.bodySmall,
                    color = color
                )
            }
        }
    }
}