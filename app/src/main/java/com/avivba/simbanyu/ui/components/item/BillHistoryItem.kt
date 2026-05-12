package com.avivba.simbanyu.ui.components.item

import androidx.annotation.DrawableRes
import androidx.compose.foundation.background
import androidx.compose.foundation.combinedClickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import com.avivba.simbanyu.R
import com.avivba.simbanyu.ui.components.buttons.SimbanyuButton
import com.avivba.simbanyu.ui.components.text.SimbanyuText
import com.avivba.simbanyu.ui.theme.SimbanyuTheme
import com.avivba.simbanyu.ui.utils.conditional

@Composable
fun BillHistoryItem(
    modifier: Modifier = Modifier,
    period: String,
    amount: String,
    status: String,
    statusColor: Color = SimbanyuTheme.colors.brandText,
    statusBackgroundColor: Color = SimbanyuTheme.colors.brand,
    @DrawableRes iconId: Int,
    iconTint: Color = SimbanyuTheme.colors.brand,
    iconBackgroundColor: Color = SimbanyuTheme.colors.surfaceCard,
    canPayByCash: Boolean = false,
    onClick: () -> Unit = {},
    onLongClick: () -> Unit = {},
    isDisabled: Boolean = false,
    onClickPayByCash: () -> Unit = {},
) {
    Box(
        modifier = modifier
            .fillMaxWidth()
            .background(
                color = SimbanyuTheme.colors.surfaceCardAlternate,
                shape = SimbanyuTheme.shapes.large
            )
            .clip(shape = SimbanyuTheme.shapes.large)
            .conditional(!isDisabled) {
                combinedClickable(
                    onClick = onClick,
                    onLongClick = onLongClick
                )
            }
    ) {
        Column(
            modifier = Modifier
                .padding(SimbanyuTheme.padding.large)
                .fillMaxWidth(),
            verticalArrangement = Arrangement.spacedBy(SimbanyuTheme.spacing.medium)
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(SimbanyuTheme.spacing.large),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Icon(
                    painter = painterResource(iconId),
                    contentDescription = null,
                    tint = iconTint,
                    modifier = Modifier
                        .background(
                            color = iconBackgroundColor,
                            shape = CircleShape
                        )
                        .padding(SimbanyuTheme.padding.medium)
                )
                Column(
                    modifier = Modifier.weight(1f),
                    verticalArrangement = Arrangement.spacedBy(SimbanyuTheme.spacing.extraSmall)
                ) {
                    SimbanyuText(
                        text = period,
                        style = SimbanyuTheme.typography.titleSection,
                        color = SimbanyuTheme.colors.textHeading
                    )
                }
                Column(
                    horizontalAlignment = Alignment.End,
                    verticalArrangement = Arrangement.spacedBy(SimbanyuTheme.spacing.small)
                ) {
                    SimbanyuText(
                        text = amount,
                        style = SimbanyuTheme.typography.titleCard,
                        color = SimbanyuTheme.colors.textHeading
                    )
                    SimbanyuText(
                        modifier = Modifier
                            .background(
                                color = statusBackgroundColor,
                                shape = SimbanyuTheme.shapes.medium
                            )
                            .padding(
                                vertical = SimbanyuTheme.padding.extraSmall / 2,
                                horizontal = SimbanyuTheme.padding.small
                            ),
                        text = status,
                        style = SimbanyuTheme.typography.labelBadge,
                        color = statusColor
                    )
                }
            }

            if (canPayByCash) {
                SimbanyuButton(
                    modifier = Modifier
                        .fillMaxWidth()
                        .background(
                            brush = SimbanyuTheme.colors.brush.brandVertical,
                            shape = SimbanyuTheme.shapes.extraLarge
                        ),
                    text = "Bayar Tunai",
                    textColor = SimbanyuTheme.colors.brandText,
                    iconLeft = {
                        Icon(
                            painter = painterResource(R.drawable.ic_payments_filled),
                            contentDescription = null,
                            tint = SimbanyuTheme.colors.brandText
                        )
                    },
                    onClick = onClickPayByCash
                )
            }
        }

        if (isDisabled) {
            Box(
                modifier = Modifier
                    .matchParentSize()
                    .background(SimbanyuTheme.colors.statusNeutral.copy(alpha = 0.4f))
            )
        }
    }
}

@Preview
@Composable
private fun BillHistoryItemPreview() {
    BillHistoryItem(
        period = "Januari 2025",
        iconId = R.drawable.ic_history,
        amount = "150.000",
        status = "LUNAS",
        canPayByCash = true,
    )
}
