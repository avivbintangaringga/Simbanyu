package com.avivba.simbanyu.ui.components.item

import androidx.compose.animation.AnimatedVisibility
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
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.avivba.simbanyu.R
import com.avivba.simbanyu.ui.components.imageview.ImageView
import com.avivba.simbanyu.ui.components.text.SimbanyuText
import com.avivba.simbanyu.ui.screens.usagehistory.fakeUsages
import com.avivba.simbanyu.ui.shared.model.UiUsage
import com.avivba.simbanyu.ui.theme.SimbanyuTheme

@Composable
fun UsageHistoryItem(
    modifier: Modifier = Modifier,
    usage: UiUsage,
    onClick: () -> Unit = {}
) {
    Column(
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
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(
                SimbanyuTheme.spacing.large
            ),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Icon(
                painter = painterResource(
                    R.drawable.ic_water_droplet
                ),
                contentDescription = null,
                tint = SimbanyuTheme.colors.brand,
                modifier = Modifier
                    .background(
                        color = SimbanyuTheme.colors.brandText,
                        shape = CircleShape
                    )
                    .padding(SimbanyuTheme.padding.medium)
            )
            Column(
                modifier = Modifier
                    .weight(1f)
            ) {
                SimbanyuText(
                    text = "${usage.periodMonth} ${usage.periodYear}",
                    style = SimbanyuTheme.typography.bodyProminent
                )
                Spacer(modifier = Modifier.height(SimbanyuTheme.spacing.small))
                SimbanyuText(
                    text = "METER: ${usage.meterStart} - ${usage.meterEnd}",
                    style = SimbanyuTheme.typography.bodySmall
                )
            }
            SimbanyuText(
                text = "${usage.amount} m³",
                style = SimbanyuTheme.typography.displayMedium
            )

        }

        AnimatedVisibility(
            visible = usage.isExpanded
        ) {
            Column(
                modifier = Modifier.fillMaxWidth(),
                verticalArrangement = Arrangement.spacedBy(
                    SimbanyuTheme.spacing.medium
                )
            ) {
                Row(
                    modifier = Modifier.padding(top = SimbanyuTheme.padding.medium),
                    verticalAlignment = Alignment.CenterVertically,
                ) {
                    Icon(
                        modifier = Modifier
                            .height(16.dp)
                            .padding(end = SimbanyuTheme.padding.medium),
                        painter = painterResource(R.drawable.ic_photo_camera_filled),
                        contentDescription = null,
                        tint = SimbanyuTheme.colors.brand
                    )
                    SimbanyuText(
                        text = "BUKTI FOTO",
                        style = SimbanyuTheme.typography.labelOverline
                    )
                }

                ImageView(
                    model = usage.imageUrl,
                    modifier = Modifier.fillMaxWidth()
                )

                if (usage.operatorNotes.isNotEmpty()) {
                    Row(
                        modifier = Modifier.padding(top = SimbanyuTheme.padding.medium),
                        verticalAlignment = Alignment.CenterVertically,
                    ) {
                        Icon(
                            modifier = Modifier
                                .height(16.dp)
                                .padding(end = SimbanyuTheme.padding.medium),
                            painter = painterResource(R.drawable.ic_description_filled),
                            contentDescription = null,
                            tint = SimbanyuTheme.colors.brand
                        )
                        SimbanyuText(
                            text = "CATATAN PETUGAS",
                            style = SimbanyuTheme.typography.labelOverline
                        )
                    }

                    SimbanyuText(
                        text = usage.operatorNotes
                    )
                }
            }
        }

        Row(
            modifier = Modifier
                .padding(
                    top = SimbanyuTheme.spacing.medium
                )
                .fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(
                SimbanyuTheme.spacing.medium,
                alignment = Alignment.CenterHorizontally
            )
        ) {
            SimbanyuText(
                text = if (usage.isExpanded) {
                    "Sembunyikan detail"
                } else {
                    "Tampilkan detail"
                },
                style = SimbanyuTheme.typography.labelUnit,
                color = SimbanyuTheme.colors.brand
            )
            Icon(
                painter = painterResource(
                    if (usage.isExpanded) {
                        R.drawable.ic_keyboard_arrow_up
                    } else {
                        R.drawable.ic_keyboard_arrow_down
                    }
                ),
                contentDescription = null,
                tint = SimbanyuTheme.colors.brand,
                modifier = Modifier.height(20.dp)
            )
        }
    }
}

@Preview
@Composable
private fun UsageHistoryItemPreview() {
    UsageHistoryItem(
        usage = fakeUsages[0].copy(
            isExpanded = true,
            operatorNotes = "Meteran hilang"
        )
    )
}