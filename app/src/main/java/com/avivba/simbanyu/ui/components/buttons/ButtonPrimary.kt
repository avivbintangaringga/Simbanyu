package com.avivba.simbanyu.ui.components.buttons

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.defaultMinSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.LocalContentColor
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.avivba.simbanyu.ui.theme.Blue30
import com.avivba.simbanyu.ui.theme.Blue40
import com.avivba.simbanyu.ui.theme.SimbanyuTheme

@Composable
fun ButtonPrimary(
    modifier: Modifier = Modifier,
    iconLeft: @Composable (() -> Unit)? = null,
    iconRight: @Composable (() -> Unit)? = null,
    text: String,
    onClick: () -> Unit
) {
    Surface(
        onClick = onClick,
        modifier = modifier,
        shape = ButtonDefaults.shape,
        color = Color.Transparent
    ) {
        Box(
            modifier = Modifier
                .background(
                    brush = Brush.verticalGradient(
                        0.0f to Blue30,
                        1.0f to Blue40
                    )
                )
                .defaultMinSize(
                    minWidth = ButtonDefaults.MinWidth,
                    minHeight = ButtonDefaults.MinHeight
                )
                .padding(PaddingValues(horizontal = 24.dp, vertical = 8.dp)),
            contentAlignment = Alignment.Center
        ) {
            CompositionLocalProvider(LocalContentColor provides Color.White) {
                Row(
                    horizontalArrangement = Arrangement.spacedBy(
                        space = 8.dp,
                        alignment = Alignment.CenterHorizontally
                    ),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    iconLeft?.invoke()
                    Text(text)
                    iconRight?.invoke()
                }
            }
        }
    }
}

@Preview
@Composable
fun ButtonPrimaryPreview() {
    ButtonPrimary(
        modifier = Modifier.padding(32.dp),
        text = "Hello World!",
        iconRight = {
            Text("XX")
        },
        onClick = {}
    )
}
