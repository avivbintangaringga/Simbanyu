package com.avivba.simbanyu.ui.components.buttons

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.defaultMinSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import com.avivba.simbanyu.ui.components.text.SimbanyuText
import com.avivba.simbanyu.ui.theme.SimbanyuTheme

@Composable
fun SimbanyuButtonBase(
    modifier: Modifier = Modifier,
    iconLeft: @Composable (() -> Unit)? = null,
    iconRight: @Composable (() -> Unit)? = null,
    text: String,
    textColor: Color = SimbanyuTheme.colors.onPrimary,
    onClick: () -> Unit
) {
    Surface(
        onClick = onClick,
        modifier = modifier
            .defaultMinSize(
                minWidth = ButtonDefaults.MinWidth,
                minHeight = ButtonDefaults.MinHeight
            )
            .padding(horizontal = SimbanyuTheme.padding.large),
        shape = SimbanyuTheme.shapes.extraLarge,
        color = Color.Transparent
    ) {
        Row(
            horizontalArrangement = Arrangement.spacedBy(
                space = SimbanyuTheme.padding.medium,
                alignment = Alignment.CenterHorizontally
            ),
            verticalAlignment = Alignment.CenterVertically
        ) {
            iconLeft?.invoke()
            SimbanyuText(
                text = text,
                style = SimbanyuTheme.typography.button,
                color = textColor
            )
            iconRight?.invoke()
        }
    }
}

@Preview
@Composable
fun SimbanyuButtonBasePreview() {
    SimbanyuButtonBase(
        modifier = Modifier,
        text = "Hello World!",
        iconRight = {
            SimbanyuText("XX")
        },
        onClick = {}
    )
}
