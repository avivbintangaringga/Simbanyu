package com.avivba.simbanyu.ui.components.buttons

import androidx.annotation.DrawableRes
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Icon
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
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
    iconTint: Color = SimbanyuTheme.colors.brand,
    iconBackgroundColor: Color = SimbanyuTheme.colors.brandText,
    color: Color = SimbanyuTheme.colors.textBody,
    cardColor: Color = SimbanyuTheme.colors.surfaceCardAlternate,
    shape: Shape = SimbanyuTheme.shapes.extraLarge,
    onClick: () -> Unit = {},
) {
    Surface(
        onClick = onClick,
        modifier = modifier
            .height(IntrinsicSize.Min),
        color = cardColor,
        shape = shape,
    ) {
        Column(
            modifier = Modifier
                .padding(SimbanyuTheme.padding.large),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            if (iconId != null) {
                Icon(
                    modifier = Modifier
                        .padding(
                            bottom = SimbanyuTheme.padding.medium
                        ).size(40.dp)
                        .background(
                            color = iconBackgroundColor,
                            shape = CircleShape
                        )
                        .padding(
                            SimbanyuTheme.padding.medium
                        ),
                    painter = painterResource(iconId),
                    contentDescription = null,
                    tint = iconTint
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