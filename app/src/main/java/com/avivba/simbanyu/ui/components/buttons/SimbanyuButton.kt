package com.avivba.simbanyu.ui.components.buttons

import androidx.compose.foundation.background
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import com.avivba.simbanyu.ui.theme.SimbanyuTheme

@Composable
fun SimbanyuButton(
    modifier: Modifier = Modifier,
    backgroundColor: Color = SimbanyuTheme.colors.primary,
    textColor: Color = SimbanyuTheme.colors.onPrimary,
    iconLeft: @Composable (() -> Unit)? = null,
    iconRight: @Composable (() -> Unit)? = null,
    text: String,
    onClick: () -> Unit
) {
    SimbanyuButtonBase(
        modifier = modifier
            .background(
                color = backgroundColor,
                shape = SimbanyuTheme.shapes.extraLarge
            ),
        iconLeft = iconLeft,
        iconRight = iconRight,
        text = text,
        textColor = textColor,
        onClick = onClick
    )
}
