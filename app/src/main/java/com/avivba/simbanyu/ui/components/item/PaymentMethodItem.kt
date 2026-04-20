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
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.painterResource
import com.avivba.simbanyu.R
import com.avivba.simbanyu.ui.components.text.SimbanyuText
import com.avivba.simbanyu.ui.screens.paymentmethod.PaymentMethod
import com.avivba.simbanyu.ui.screens.paymentmethod.PaymentMethodUiAction
import com.avivba.simbanyu.ui.theme.SimbanyuTheme

@Composable
fun PaymentMethodItem(
    modifier: Modifier = Modifier,
    method: PaymentMethod,
    selected: Boolean = false,
    onClick: () -> Unit,
) {
    Box(
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
    ) {
        Row(
            modifier = Modifier
                .padding(SimbanyuTheme.padding.large),
            horizontalArrangement = Arrangement.spacedBy(
                SimbanyuTheme.spacing.large
            ),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Icon(
                painter = painterResource(R.drawable.ic_payment),
                contentDescription = null,
                tint = SimbanyuTheme.colors.brand,
                modifier = Modifier
                    .background(
                        color = SimbanyuTheme.colors.brand.copy(alpha = 0.3f),
                        shape = CircleShape
                    )
                    .padding(SimbanyuTheme.padding.medium)
            )

            Column(
                modifier = Modifier
                    .weight(1f)
            ) {
                SimbanyuText(
                    text = method.name,
                    style = SimbanyuTheme.typography.bodyProminent
                )
                Spacer(modifier = Modifier.height(SimbanyuTheme.spacing.small))
                SimbanyuText(
                    text = "Biaya layanan: ${method.fee}",
                    style = SimbanyuTheme.typography.bodySmall
                )
            }

            var checkIcon = R.drawable.ic_circle
            var iconTint = SimbanyuTheme.colors.statusNeutral

            if (selected) {
                checkIcon = R.drawable.ic_check_circle_filled
                iconTint = SimbanyuTheme.colors.brand
            }

            Icon(
                painter = painterResource(checkIcon),
                contentDescription = null,
                tint = iconTint,
            )
        }
    }
}