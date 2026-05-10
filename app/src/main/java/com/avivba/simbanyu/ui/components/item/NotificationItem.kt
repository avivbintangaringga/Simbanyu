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
import androidx.compose.foundation.layout.requiredWidth
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import com.avivba.simbanyu.ui.components.text.SimbanyuText
import com.avivba.simbanyu.ui.shared.model.UiNotification
import com.avivba.simbanyu.ui.theme.SimbanyuTheme

@Composable
fun NotificationItem(
    modifier: Modifier = Modifier,
    notification: UiNotification,
    onClick: () -> Unit
) {
    Box(
        modifier = modifier,
        contentAlignment = Alignment.BottomCenter
    ) {
        Column(
            modifier = Modifier
                .clickable {
                    onClick()
                }
                .padding(
                    vertical = SimbanyuTheme.spacing.medium,
                    horizontal = SimbanyuTheme.padding.medium
                ),
            verticalArrangement = Arrangement.spacedBy(
                SimbanyuTheme.spacing.small
            )
        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(
                    SimbanyuTheme.spacing.medium
                )
            ) {
                if (!notification.isRead) {
                    Box(
                        modifier = Modifier
                            .requiredWidth(8.dp)
                            .height(8.dp)
                            .clip(CircleShape)
                            .background(SimbanyuTheme.colors.brand)
                    )
                }

                SimbanyuText(
                    modifier = Modifier
                        .weight(1f),
                    text = notification.title,
                    style = SimbanyuTheme.typography.bodyProminent,
                    color = SimbanyuTheme.colors.textHeading
                )

            }

            SimbanyuText(
                modifier = Modifier,
                text = notification.body,
                style = SimbanyuTheme.typography.bodyStandard,
                maxLines = 2,
                overflow = TextOverflow.Ellipsis
            )

            SimbanyuText(
                modifier = Modifier,
                text = notification.date,
                style = SimbanyuTheme.typography.bodySmall,
                color = SimbanyuTheme.colors.textInputDisabled,
                fontStyle = FontStyle.Italic
            )

        }

        Spacer(
            Modifier
                .height(1.dp)
                .fillMaxWidth()
                .background(
                    SimbanyuTheme.colors.borderCard
                )
        )
    }
}