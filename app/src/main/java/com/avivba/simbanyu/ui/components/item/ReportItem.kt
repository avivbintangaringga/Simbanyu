package com.avivba.simbanyu.ui.components.item

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
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
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Devices
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.avivba.simbanyu.R
import com.avivba.simbanyu.ui.components.text.SimbanyuText
import com.avivba.simbanyu.ui.screens.report.Report
import com.avivba.simbanyu.ui.screens.report.fakeReports
import com.avivba.simbanyu.ui.theme.SimbanyuTheme

@Composable
fun ReportItem(
    modifier: Modifier = Modifier,
    report: Report,
    onClickExpand: () -> Unit = {}
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .clip(SimbanyuTheme.shapes.large)
            .background(
                color = SimbanyuTheme.colors.surfaceCardAlternate,
            )
            .clickable {
                onClickExpand()
            }.padding(
                SimbanyuTheme.padding.large
            ),
        verticalArrangement = Arrangement.spacedBy(
            SimbanyuTheme.spacing.medium
        )
    ) {
        val iconId = R.drawable.ic_water_droplet
        val iconTint = SimbanyuTheme.colors.brand
        val iconBackgroundColor = SimbanyuTheme.colors.brand.copy(alpha = 0.2f)
        val statusBackgroundColor = SimbanyuTheme.colors.brand
        val statusColor = SimbanyuTheme.colors.brandText

        Row(
            modifier = modifier
                .fillMaxWidth(),
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
                    text = report.title,
                    style = SimbanyuTheme.typography.bodyProminent
                )
                Spacer(modifier = Modifier.height(SimbanyuTheme.spacing.small))
                SimbanyuText(
                    text = report.date,
                    style = SimbanyuTheme.typography.bodySmall
                )
            }
            Column(
                modifier = Modifier,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                SimbanyuText(
                    modifier = Modifier
                        .background(
                            color = statusBackgroundColor,
                            shape = SimbanyuTheme.shapes.medium
                        )
                        .padding(
                            vertical = SimbanyuTheme.padding.extraSmall,
                            horizontal = SimbanyuTheme.padding.medium
                        ),
                    text = report.status,
                    style = SimbanyuTheme.typography.labelUnit,
                    color = statusColor
                )
            }
        }

        SimbanyuText(
            text = report.description
        )

        if (report.isExpanded) {

            Row(
                modifier = Modifier.padding(top = SimbanyuTheme.padding.medium),
                verticalAlignment = Alignment.CenterVertically,
            ) {
                Icon(
                    modifier = Modifier
                        .height(16.dp)
                        .padding(end = SimbanyuTheme.padding.medium),
                    painter = painterResource(R.drawable.ic_location),
                    contentDescription = null,
                    tint = SimbanyuTheme.colors.brand
                )
                SimbanyuText(
                    text = "LOKASI",
                    style = SimbanyuTheme.typography.labelOverline
                )
            }

            SimbanyuText(
                text = report.location
            )

            Row(
                modifier = Modifier.padding(top = SimbanyuTheme.padding.medium),
                verticalAlignment = Alignment.CenterVertically,
            ) {
                Icon(
                    modifier = Modifier
                        .height(16.dp)
                        .padding(end = SimbanyuTheme.padding.medium),
                    painter = painterResource(R.drawable.ic_camera),
                    contentDescription = null,
                    tint = SimbanyuTheme.colors.brand
                )
                SimbanyuText(
                    text = "BUKTI FOTO",
                    style = SimbanyuTheme.typography.labelOverline
                )
            }

            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(150.dp)
                    .clip(SimbanyuTheme.shapes.large)
                    .background(
                        SimbanyuTheme.colors.statusNeutral
                    )
            )

        }

        Row(
            modifier = Modifier
                .fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(
                SimbanyuTheme.spacing.medium,
                alignment = Alignment.CenterHorizontally
            )
        ) {
            SimbanyuText(
                text = if(report.isExpanded) {
                    "Sembunyikan detail"
                } else {
                    "Tampilkan detail"
                },
                style = SimbanyuTheme.typography.labelUnit,
                color = SimbanyuTheme.colors.brand
            )
            Icon(
                painter = painterResource(
                    if (report.isExpanded) {
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

@Preview(showBackground = true, device = Devices.PIXEL_7, showSystemUi = false)
@Composable
private fun ReportPreview() {
    ReportItem(
        modifier = Modifier,
        report = fakeReports[0]
    )
}