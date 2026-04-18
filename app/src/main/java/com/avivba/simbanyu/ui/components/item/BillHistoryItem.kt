package com.avivba.simbanyu.ui.components.item

import androidx.annotation.DrawableRes
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Icon
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
    onClick: () -> Unit = {}
) {
    Row(
        modifier = modifier
            .fillMaxWidth()
            .background(
                color = SimbanyuTheme.colors.surfaceCardAlternate,
                shape = SimbanyuTheme.shapes.large
            )
            .clip(
                shape = SimbanyuTheme.shapes.large
            )
            .clickable {
                onClick()
            }
            .padding(SimbanyuTheme.padding.large),
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
                    shape = SimbanyuTheme.shapes.medium
                )
                .padding(SimbanyuTheme.padding.medium)
        )
        Column(
            modifier = Modifier
                .weight(1f)
        ) {
            SimbanyuText(
                text = period,
                style = SimbanyuTheme.typography.bodyProminent
            )
            Spacer(modifier = Modifier.height(SimbanyuTheme.spacing.small))
            SimbanyuText(
                text = "ID: $id",
                style = SimbanyuTheme.typography.bodySmall
            )
        }
        Column(
            modifier = Modifier,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            SimbanyuText(
                text = "Rp. $amount",
                style = SimbanyuTheme.typography.titleCard
            )
            Spacer(modifier = Modifier.height(SimbanyuTheme.spacing.small))
            SimbanyuText(
                modifier = Modifier
                    .background(
                        color = statusBackgroundColor,
                        shape = SimbanyuTheme.shapes.medium
                    ).padding(
                        vertical = SimbanyuTheme.padding.extraSmall,
                        horizontal = SimbanyuTheme.padding.medium
                    ),
                text = status,
                style = SimbanyuTheme.typography.labelUnit,
                color = statusColor
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
        status = "LUNAS"
    )
}