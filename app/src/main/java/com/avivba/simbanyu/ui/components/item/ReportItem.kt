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
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Devices
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.avivba.simbanyu.R
import com.avivba.simbanyu.ui.components.buttons.SimbanyuButton
import com.avivba.simbanyu.ui.components.imageview.ImageView
import com.avivba.simbanyu.ui.components.minimap.MiniMap
import com.avivba.simbanyu.ui.components.text.SimbanyuText
import com.avivba.simbanyu.ui.screens.operator.subscribers.fakeSubscribers
import com.avivba.simbanyu.ui.shared.model.UiCoordinate
import com.avivba.simbanyu.ui.shared.model.UiReport
import com.avivba.simbanyu.ui.shared.model.UiReportStatus
import com.avivba.simbanyu.ui.theme.SimbanyuTheme

@Composable
fun ReportItem(
    modifier: Modifier = Modifier,
    report: UiReport,
    showHandleReport: Boolean = false,
    showUser: Boolean = false,
    onClickExpand: () -> Unit = {},
    onClickHandleReport: () -> Unit = {}
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
            }
            .padding(
                SimbanyuTheme.padding.large
            ),
        verticalArrangement = Arrangement.spacedBy(
            SimbanyuTheme.spacing.medium
        )
    ) {
        val iconId = R.drawable.ic_feedback_filled
        val iconTint = SimbanyuTheme.colors.brand
        val iconBackgroundColor = SimbanyuTheme.colors.brand.copy(alpha = 0.2f)

        var statusBackgroundColor: Color
        when (report.status) {
            UiReportStatus.WAITING -> {
                statusBackgroundColor = SimbanyuTheme.colors.statusNeutral
            }

            UiReportStatus.IN_PROGRESS -> {
                statusBackgroundColor = SimbanyuTheme.colors.statusWarningText
            }

            UiReportStatus.COMPLETED -> {
                statusBackgroundColor = SimbanyuTheme.colors.statusPositiveText
            }

            UiReportStatus.REJECTED -> {
                statusBackgroundColor = SimbanyuTheme.colors.statusNegativeText
            }
        }

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
                    text = "Pengaduan layanan", // TODO: Do something about the title
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
                    text = report.status.status,
                    style = SimbanyuTheme.typography.labelUnit,
                    color = SimbanyuTheme.colors.brandText
                )
            }
        }

        SimbanyuText(
            text = report.description
        )

        if (showUser) {
            Row(
                modifier = Modifier.padding(top = SimbanyuTheme.padding.medium),
                verticalAlignment = Alignment.CenterVertically,
            ) {
                Icon(
                    modifier = Modifier
                        .height(16.dp)
                        .padding(end = SimbanyuTheme.padding.medium),
                    painter = painterResource(R.drawable.ic_person_filled),
                    contentDescription = null,
                    tint = SimbanyuTheme.colors.brand
                )
                SimbanyuText(
                    text = "PELAPOR",
                    style = SimbanyuTheme.typography.labelOverline
                )
            }

            SimbanyuText(
                text = "${report.subscriber.name} (${report.subscriber.meterId})"
            )
        }

        AnimatedVisibility(
            visible = report.isExpanded
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
                        painter = painterResource(R.drawable.ic_location_on_filled),
                        contentDescription = null,
                        tint = SimbanyuTheme.colors.brand
                    )
                    SimbanyuText(
                        text = "LOKASI",
                        style = SimbanyuTheme.typography.labelOverline
                    )
                }

                SimbanyuText(
                    text = report.address
                )

                if (report.coordinate != null) {
                    MiniMap(
                        modifier = Modifier,
                        location = report.coordinate
                    )
                }

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
                    model = report.evidenceImageUrl,
                    modifier = Modifier.fillMaxWidth()
                )

                if (report.operatorNotes.isNotEmpty()) {
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
                        text = report.operatorNotes
                    )
                }

                if (report.handlingEvidenceImageUrl.isNotEmpty()) {
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
                            text = "BUKTI PENANGANAN",
                            style = SimbanyuTheme.typography.labelOverline
                        )
                    }

                    ImageView(
                        model = report.handlingEvidenceImageUrl,
                        modifier = Modifier.fillMaxWidth()
                    )
                }

                if (showHandleReport) {
                    SimbanyuButton(
                        modifier = Modifier
                            .fillMaxWidth()
                            .background(
                                brush = SimbanyuTheme.colors.brush.brandVertical
                            ),
                        text = "Update Penanganan",
                        onClick = onClickHandleReport
                    )
                }
            }
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
                text = if (report.isExpanded) {
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
        showHandleReport = true,
        showUser = true,
        report = UiReport(
            id = "REP-001",
            status = UiReportStatus.WAITING,
            address = "Jl. Merdeka No. 10, Jakarta",
            coordinate = UiCoordinate(-6.1754, 106.8272),
            description = "Pipa air depan rumah pecah, air meluap ke jalanan.",
            evidenceImageUrl = "https://example.com/leak1.jpg",
            date = "24 Mei 2024",
            isExpanded = true,
            subscriber = fakeSubscribers[0],
            operatorNotes = "",
            handlingEvidenceImageUrl = "",
        ),
    )
}