package com.avivba.simbanyu.ui.components.buttons

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.defaultMinSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.LocalContentColor
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.avivba.simbanyu.ui.components.text.SimbanyuText
import com.avivba.simbanyu.ui.theme.SimbanyuTheme

@Composable
fun SimbanyuGradientButton(
    modifier: Modifier = Modifier,
    iconLeft: @Composable (() -> Unit)? = null,
    iconRight: @Composable (() -> Unit)? = null,
    text: String,
    onClick: () -> Unit
) {
    SimbanyuButtonBase(
        modifier = modifier
            .background(
                brush = SimbanyuTheme.colors.primaryGradientVerticalBrush,
                shape = SimbanyuTheme.shapes.extraLarge
            ),
        iconLeft = iconLeft,
        iconRight = iconRight,
        text = text,
        textColor = SimbanyuTheme.colors.onPrimary,
        onClick = onClick
    )
}

@Preview
@Composable
fun SimbanyuGradientButtonPreview() {
    SimbanyuGradientButton(
        modifier = Modifier.padding(32.dp),
        text = "Hello World!",
        iconRight = {
            SimbanyuText("XX")
        },
        onClick = {}
    )
}
