package com.avivba.simbanyu.ui.components.item

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
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
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
    Column(
        modifier = modifier
            .clip(
                SimbanyuTheme.shapes.large
            )
            .clickable {
                onClick()
            }
            .background(
                SimbanyuTheme.colors.surfaceCardAlternate
            )
            .fillMaxWidth()
            .padding(
                SimbanyuTheme.padding.large
            ),
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(
                SimbanyuTheme.spacing.medium
            ),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Icon(
                modifier = Modifier
                    .size(40.dp)
                    .background(
                        color = SimbanyuTheme.colors.brandText,
                        shape = CircleShape
                    )
                    .padding(
                        SimbanyuTheme.padding.medium
                    ),
                painter = painterResource(R.drawable.ic_person_filled),
                contentDescription = null,
                tint = SimbanyuTheme.colors.brand
            )

            Column(
                modifier = Modifier
                    .fillMaxWidth(),
                verticalArrangement = Arrangement.spacedBy(
                    SimbanyuTheme.spacing.small
                )
            ) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {

                    SimbanyuText(
                        text = subscriber.name,
                        style = SimbanyuTheme.typography.displaySmall
                    )

                    var status = "BELUM DICATAT"
                    var iconId = R.drawable.ic_warning_filled
                    var color = SimbanyuTheme.colors.statusWarningText

                    if (subscriber.isRecorded) {
                        status = "SELESAI"
                        iconId = R.drawable.ic_check
                        color = SimbanyuTheme.colors.statusPositiveText
                    }

                    Row(
                        modifier = Modifier
                            .background(
                                color = color,
                                shape = SimbanyuTheme.shapes.large
                            )
                            .padding(
                                vertical = SimbanyuTheme.padding.extraSmall,
                                horizontal = SimbanyuTheme.padding.medium
                            ),
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.spacedBy(
                            SimbanyuTheme.spacing.small
                        )
                    ) {
                        Icon(
                            modifier = Modifier
                                .size(12.dp),
                            painter = painterResource(iconId),
                            contentDescription = null,
                            tint = SimbanyuTheme.colors.brandText,
                        )

                        SimbanyuText(
                            text = status,
                            style = SimbanyuTheme.typography.bodySmall,
                            color = SimbanyuTheme.colors.brandText,
                        )
                    }
                }

                SimbanyuText(
                    text = subscriber.address,
                    style = SimbanyuTheme.typography.bodySmall
                )
            }
        }

        Spacer(Modifier.height(SimbanyuTheme.spacing.medium))

        Row(
            modifier = Modifier,
            horizontalArrangement = Arrangement.spacedBy(
                SimbanyuTheme.spacing.small
            ),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Icon(
                modifier = Modifier
                    .height(16.dp),
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
            modifier = Modifier
                .fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Column(
                modifier = Modifier
                    .weight(1f),
                verticalArrangement = Arrangement.spacedBy(
                    SimbanyuTheme.spacing.extraSmall
                )
            ) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(
                        SimbanyuTheme.spacing.small
                    ),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Icon(
                        modifier = Modifier
                            .height(16.dp),
                        painter = painterResource(R.drawable.ic_calendar_clock_filled),
                        contentDescription = null,
                        tint = SimbanyuTheme.colors.brand
                    )

                    SimbanyuText(
                        text = "CATAT TERAKHIR",
                        style = SimbanyuTheme.typography.bodySmall.copy(
                            fontWeight = FontWeight(900)
                        )
                    )
                }
                SimbanyuText(
                    text = subscriber.lastMeterRecordDate,
                    style = SimbanyuTheme.typography.bodySmall
                )
            }

            if (subscriber.isRecorded) {
                SimbanyuButton(
                    text = "Perbarui",
                    modifier = Modifier
                        .background(
                            brush = Brush.verticalGradient(
                                colors = listOf(
                                    SimbanyuTheme.colors.statusNeutral,
                                    SimbanyuTheme.colors.statusNeutral.copy(
                                        alpha = 0.6f
                                    )
                                )
                            ),
                        ),
                    iconLeft = {
                        Icon(
                            painter = painterResource(R.drawable.ic_speed_filled),
                            contentDescription = null,
                            tint = SimbanyuTheme.colors.actionBrandText
                        )
                    },
                    onClick = {
                        onClickRecordMeter()
                    }
                )
            } else {
                SimbanyuButton(
                    text = "Catat",
                    modifier = Modifier
                        .background(
                            brush = SimbanyuTheme.colors.brush.brandVertical,
                        ),
                    iconLeft = {
                        Icon(
                            painter = painterResource(R.drawable.ic_speed_filled),
                            contentDescription = null,
                            tint = SimbanyuTheme.colors.actionBrandText
                        )
                    },
                    onClick = {
                        onClickRecordMeter()
                    }
                )
            }
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