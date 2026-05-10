package com.avivba.simbanyu.ui.components.item

import androidx.annotation.DrawableRes
import androidx.compose.foundation.background
import androidx.compose.foundation.combinedClickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
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
    id: String,
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
            .height(IntrinsicSize.Min)
            .fillMaxWidth()
            .background(
                color = SimbanyuTheme.colors.surfaceCardAlternate,
                shape = SimbanyuTheme.shapes.large
            )
            .clip(
                shape = SimbanyuTheme.shapes.large
            )
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
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(
                SimbanyuTheme.spacing.medium
            )
        ) {
            Row(
                modifier = Modifier,
                horizontalArrangement = Arrangement.spacedBy(
                    SimbanyuTheme.spacing.large
                ),
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
                    modifier = Modifier
                        .weight(1f)
                ) {
                    SimbanyuText(
                        text = period,
                        style = SimbanyuTheme.typography.titleSection
                    )
//                    Spacer(modifier = Modifier.height(SimbanyuTheme.spacing.small))
//                    SimbanyuText(
//                        text = "ID: $id",
//                        style = SimbanyuTheme.typography.bodySmall
//                    )
                }
                Column(
                    modifier = Modifier,
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    SimbanyuText(
                        text = amount,
                        style = SimbanyuTheme.typography.titleCard
                    )
                    Spacer(modifier = Modifier.height(SimbanyuTheme.spacing.small))
                    SimbanyuText(
                        modifier = Modifier
                            .background(
                                color = statusBackgroundColor,
                                shape = SimbanyuTheme.shapes.large
                            )
                            .padding(
                                vertical = SimbanyuTheme.padding.extraSmall,
                                horizontal = SimbanyuTheme.padding.medium
                            ),
                        text = status,
                        style = SimbanyuTheme.typography.bodySmall,
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
                ) {
                    onClickPayByCash()
                }
            }
        }

        if (isDisabled) {
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .background(SimbanyuTheme.colors.statusNeutral.copy(alpha = 0.5f))
            )
        }
    }
}

@Preview
@Composable
private fun BillHistoryItemPreview() {
    BillHistoryItem(
        id = "JK32301023",
        period = "Januari 2025",
        iconId = R.drawable.ic_history,
        amount = "150.000",
        status = "LUNAS",
        canPayByCash = true,
    )
}