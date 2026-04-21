package com.avivba.simbanyu.ui.components.item

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.avivba.simbanyu.R
import com.avivba.simbanyu.ui.components.text.SimbanyuText
import com.avivba.simbanyu.ui.shared.model.ActivityData
import com.avivba.simbanyu.ui.theme.SimbanyuTheme

@Composable
fun ActivityItem(
    modifier: Modifier = Modifier,
    activity: ActivityData
) {
    Row(
        modifier = modifier
            .fillMaxWidth()
            .clip(
                SimbanyuTheme.shapes.large
            )
            .background(
                color = SimbanyuTheme.colors.surfaceCardAlternate,
                shape = SimbanyuTheme.shapes.large
            )
            .padding(
                SimbanyuTheme.padding.large
            ),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(
            SimbanyuTheme.spacing.medium
        )
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
            painter = painterResource(R.drawable.ic_info_filled),
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
            SimbanyuText(
                text = activity.title,
                style = SimbanyuTheme.typography.bodyProminent,
            )

            SimbanyuText(
                text = activity.body,
                style = SimbanyuTheme.typography.bodySmall
            )

            SimbanyuText(
                text = activity.date,
                fontStyle = FontStyle.Italic,
                fontSize = 11.sp,
                color = SimbanyuTheme.colors.textInputDisabled
            )
        }
    }
}

@Preview(showBackground = false)
@Composable
private fun ActivityItemPreview() {
    ActivityItem(
        activity = ActivityData(
            id = "1",
            title = "Meter Dicatat #123123",
            body = "Lorem ipsum dolor sit amet, consectetur adipiscing elit",
            date = "1 jam lalu"
        )
    )
}