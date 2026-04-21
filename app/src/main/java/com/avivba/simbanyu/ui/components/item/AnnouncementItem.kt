package com.avivba.simbanyu.ui.components.item

import androidx.annotation.DrawableRes
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardColors
import androidx.compose.material3.Icon
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.avivba.simbanyu.R
import com.avivba.simbanyu.ui.components.text.SimbanyuText
import com.avivba.simbanyu.ui.theme.SimbanyuTheme


@Composable
fun AnnouncementItem(
    modifier: Modifier = Modifier,
    title: String,
    content: String,
    time: String,
    @DrawableRes iconId: Int,
    backgroundColor: Color = SimbanyuTheme.colors.surfaceCardAlternate,
    borderColor: Color = SimbanyuTheme.colors.borderCardAlternate,
    iconTint: Color = SimbanyuTheme.colors.brand,
    iconBackgroundColor: Color = SimbanyuTheme.colors.surfaceCard,
    color: Color = SimbanyuTheme.colors.textBody,
    shape: Shape = SimbanyuTheme.shapes.extraLarge,
    onClick: () -> Unit = {},
    canDelete: Boolean = false,
    onDelete: () -> Unit = {}
) {
    Surface(
        modifier = modifier,
        onClick = onClick,
        shape = shape,
        color = backgroundColor,
    ) {
        Row(
            modifier = Modifier
                .padding(
                    SimbanyuTheme.padding.large
                )
                .fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(
                SimbanyuTheme.spacing.large
            ),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Icon(
                modifier = Modifier
                    .background(
                        color = iconBackgroundColor,
                        shape = CircleShape
                    )
                    .padding(SimbanyuTheme.padding.medium),
                painter = painterResource(iconId),
                contentDescription = null,
                tint = iconTint
            )
            Column(
                modifier = Modifier
                    .weight(1f),
                verticalArrangement = Arrangement.spacedBy(
                    SimbanyuTheme.spacing.small
                )
            ) {
                SimbanyuText(
                    text = title,
                    style = SimbanyuTheme.typography.bodyProminent,
                    color = color
                )

                SimbanyuText(
                    text = content,
                    color = color,
                    style = SimbanyuTheme.typography.bodySmall
                )

                SimbanyuText(
                    text = time,
                    fontStyle = FontStyle.Italic,
                    fontSize = 11.sp,
                    color = SimbanyuTheme.colors.textInputDisabled
                )
            }

            if (canDelete) {
                Icon(
                    modifier = Modifier
                        .clip(CircleShape)
                        .clickable {
                            onDelete()
                        }
                        .padding(SimbanyuTheme.padding.medium),
                    painter = painterResource(R.drawable.ic_delete),
                    contentDescription = null,
                    tint = SimbanyuTheme.colors.statusNegativeText
                )
            }
        }
    }
}