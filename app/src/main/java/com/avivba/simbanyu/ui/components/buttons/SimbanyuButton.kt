package com.avivba.simbanyu.ui.components.buttons

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.defaultMinSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.avivba.simbanyu.R
import com.avivba.simbanyu.ui.components.text.SimbanyuText
import com.avivba.simbanyu.ui.theme.SimbanyuTheme

@Composable
fun SimbanyuButton(
    modifier: Modifier = Modifier,
    iconLeft: @Composable (() -> Unit)? = null,
    iconRight: @Composable (() -> Unit)? = null,
    text: String,
    textColor: Color = SimbanyuTheme.colors.actionBrandText,
    onClick: () -> Unit = {}
) {
    Surface(
        onClick = onClick,
        modifier = Modifier
            .defaultMinSize(
                minWidth = ButtonDefaults.MinWidth,
                minHeight = ButtonDefaults.MinHeight
            )
            .heightIn(
                min = 48.dp
            )
            .clip(SimbanyuTheme.shapes.extraLarge)
            .then(modifier),
        shape = SimbanyuTheme.shapes.extraLarge,
        color = Color.Transparent
    ) {
        Row(
            modifier = Modifier
                .padding(horizontal = SimbanyuTheme.padding.large),
            horizontalArrangement = Arrangement.spacedBy(
                space = SimbanyuTheme.padding.medium,
                alignment = Alignment.CenterHorizontally
            ),
            verticalAlignment = Alignment.CenterVertically
        ) {
            iconLeft?.invoke()
            SimbanyuText(
                text = text,
                style = SimbanyuTheme.typography.labelAction,
                color = textColor
            )
            iconRight?.invoke()
        }
    }
}

@Preview
@Composable
fun SimbanyuButtonBasePreview() {
    SimbanyuButton(
        modifier = Modifier
            .fillMaxWidth()
            .background(
                brush = SimbanyuTheme.colors.brush.brandVertical
            ), text = "Login", iconRight = {
            Icon(
                modifier = Modifier.height(18.dp),
                painter = painterResource(R.drawable.ic_login),
                contentDescription = "Login",
                tint = SimbanyuTheme.colors.actionBrandText
            )
        }
    ) {}
}
