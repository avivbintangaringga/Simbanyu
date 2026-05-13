package com.avivba.simbanyu.ui.components.item

import androidx.compose.foundation.background
import androidx.compose.foundation.basicMarquee
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.avivba.simbanyu.R
import com.avivba.simbanyu.ui.components.buttons.SimbanyuButton
import com.avivba.simbanyu.ui.components.text.SimbanyuText
import com.avivba.simbanyu.ui.screens.operator.subscribers.fakeSubscribers
import com.avivba.simbanyu.ui.shared.model.UiSubscriber
import com.avivba.simbanyu.ui.theme.SimbanyuTheme

@Composable
fun SubscriberItem(
    modifier: Modifier = Modifier,
    subscriber: UiSubscriber,
    onClick: () -> Unit = {},
    onClickRecordMeter: () -> Unit = {},
) {
    val (status, iconId, statusColor) = if (subscriber.isRecorded) {
        Triple("SELESAI", R.drawable.ic_check, SimbanyuTheme.colors.statusPositiveText)
    } else {
        Triple(
            "BELUM DICATAT",
            R.drawable.ic_warning_filled,
            SimbanyuTheme.colors.statusWarningText
        )
    }

    Column(
        modifier = modifier
            .clip(SimbanyuTheme.shapes.large)
            .clickable { onClick() }
            .background(SimbanyuTheme.colors.surfaceCardAlternate)
            .fillMaxWidth()
            .padding(SimbanyuTheme.padding.large),
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(SimbanyuTheme.spacing.medium),
            verticalAlignment = Alignment.Top
        ) {
            Icon(
                modifier = Modifier
                    .size(40.dp)
                    .background(
                        color = SimbanyuTheme.colors.brandText,
                        shape = CircleShape
                    )
                    .padding(SimbanyuTheme.padding.medium),
                painter = painterResource(R.drawable.ic_person_filled),
                contentDescription = null,
                tint = SimbanyuTheme.colors.brand
            )

            Column(
                modifier = Modifier.weight(1f),
                verticalArrangement = Arrangement.spacedBy(SimbanyuTheme.spacing.extraSmall)
            ) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.Top
                ) {
                    SimbanyuText(
                        modifier = Modifier
                            .weight(1f)
                            .basicMarquee(),
                        text = subscriber.name,
                        style = SimbanyuTheme.typography.displaySmall,
                        maxLines = 1,
                        overflow = TextOverflow.Clip,
                    )

                    Row(
                        modifier = Modifier
                            .background(
                                color = statusColor,
                                shape = SimbanyuTheme.shapes.large
                            )
                            .padding(
                                vertical = SimbanyuTheme.padding.extraSmall,
                                horizontal = SimbanyuTheme.padding.medium
                            ),
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.spacedBy(SimbanyuTheme.spacing.extraSmall)
                    ) {
                        Icon(
                            modifier = Modifier.size(10.dp),
                            painter = painterResource(iconId),
                            contentDescription = null,
                            tint = SimbanyuTheme.colors.brandText,
                        )

                        SimbanyuText(
                            text = status,
                            style = SimbanyuTheme.typography.labelBadge,
                            color = SimbanyuTheme.colors.brandText,
                        )
                    }
                }

                Row(
                    verticalAlignment = Alignment.Top,
                    horizontalArrangement = Arrangement.spacedBy(SimbanyuTheme.spacing.extraSmall)
                ) {
                    Icon(
                        modifier = Modifier
                            .padding(top = 2.dp)
                            .size(14.dp),
                        painter = painterResource(R.drawable.ic_location_on_filled),
                        contentDescription = null,
                        tint = SimbanyuTheme.colors.brand
                    )
                    SimbanyuText(
                        text = subscriber.address,
                        style = SimbanyuTheme.typography.bodySmall,
                        color = SimbanyuTheme.colors.textBody.copy(alpha = 0.7f),
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis
                    )
                }
            }
        }

        Spacer(Modifier.height(SimbanyuTheme.spacing.medium))

        Row(
            modifier = Modifier,
            horizontalArrangement = Arrangement.spacedBy(SimbanyuTheme.spacing.small),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Icon(
                modifier = Modifier.height(16.dp),
                painter = painterResource(R.drawable.ic_speed_filled),
                contentDescription = null,
                tint = SimbanyuTheme.colors.brand
            )

            SimbanyuText(
                text = subscriber.meterId,
                style = SimbanyuTheme.typography.labelOverline
            )
        }

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Column(
                modifier = Modifier.weight(1f),
                verticalArrangement = Arrangement.spacedBy(SimbanyuTheme.spacing.extraSmall)
            ) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(SimbanyuTheme.spacing.small),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Icon(
                        modifier = Modifier.height(16.dp),
                        painter = painterResource(R.drawable.ic_calendar_clock_filled),
                        contentDescription = null,
                        tint = SimbanyuTheme.colors.brand
                    )

                    SimbanyuText(
                        text = "CATAT TERAKHIR",
                        style = SimbanyuTheme.typography.bodySmall.copy(
                            fontWeight = FontWeight.Black
                        )
                    )
                }
                SimbanyuText(
                    text = subscriber.lastMeterRecordDate,
                    style = SimbanyuTheme.typography.bodySmall
                )
            }

            SimbanyuButton(
                text = if (subscriber.isRecorded) "Perbarui" else "Catat",
                modifier = Modifier
                    .background(
                        brush = if (subscriber.isRecorded) {
                            Brush.verticalGradient(
                                colors = listOf(
                                    SimbanyuTheme.colors.statusNeutral,
                                    SimbanyuTheme.colors.statusNeutral.copy(alpha = 0.6f)
                                )
                            )
                        } else {
                            SimbanyuTheme.colors.brush.brandVertical
                        },
                        shape = SimbanyuTheme.shapes.extraLarge
                    ),
                iconLeft = {
                    Icon(
                        painter = painterResource(R.drawable.ic_speed_filled),
                        contentDescription = null,
                        tint = SimbanyuTheme.colors.actionBrandText
                    )
                },
                onClick = onClickRecordMeter
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun SubscriberItemPreview() {
    SubscriberItem(
        subscriber = fakeSubscribers[0]
    )
}