package com.avivba.simbanyu.ui.screens.dashboard

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.LineHeightStyle
import androidx.compose.ui.tooling.preview.Devices
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.avivba.simbanyu.R
import com.avivba.simbanyu.ui.components.buttons.SimbanyuButton
import com.avivba.simbanyu.ui.components.buttons.SimbanyuGradientButton
import com.avivba.simbanyu.ui.components.text.SimbanyuText
import com.avivba.simbanyu.ui.screens.main.Screen
import com.avivba.simbanyu.ui.theme.SimbanyuTheme

@Composable
fun DashboardScreen(
    modifier: Modifier = Modifier,
    onNavigate: (Screen) -> Unit = {}
) {
    val scrollState = rememberScrollState()
    Column(
        modifier = modifier
            .fillMaxSize()
            .verticalScroll(scrollState)
            .padding(SimbanyuTheme.padding.large),
        verticalArrangement = Arrangement.spacedBy(SimbanyuTheme.spacing.medium),
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .background(
                    SimbanyuTheme.colors.primaryContainer,
                    SimbanyuTheme.shapes.large
                )
                .padding(
                    SimbanyuTheme.padding.large
                )
        ) {
            SimbanyuText(
                text = "SELAMAT DATANG",
                style = SimbanyuTheme.typography.label,
            )

            SimbanyuText(
                text = "Budi Budiman 👋",
                style = SimbanyuTheme.typography.title
            )
        }

        Column(
            modifier = Modifier
                .fillMaxWidth()
                .background(
                    brush = SimbanyuTheme.colors.primaryGradientHorizontalBrush,
                    shape = SimbanyuTheme.shapes.extraLarge
                )
                .padding(SimbanyuTheme.padding.extraLarge),
            verticalArrangement = Arrangement.spacedBy(
                SimbanyuTheme.spacing.medium
            )
        ) {

            SimbanyuText(
                text = "TAGIHAN BELUM DIBAYAR\nPeriode Januari 2026",
                style = SimbanyuTheme.typography.label,
                color = SimbanyuTheme.colors.onPrimary
            )

            Spacer(
                modifier = Modifier.height(SimbanyuTheme.spacing.medium)
            )

            SimbanyuText(
                modifier = Modifier
                    .offset(y = SimbanyuTheme.spacing.small),
                text = "IDR",
                color = SimbanyuTheme.colors.onPrimary,
                fontSize = 18.sp
            )

            SimbanyuText(
                text = "153.000",
                style = SimbanyuTheme.typography.titleLarge.copy(
                    fontWeight = FontWeight.ExtraBold
                ),
                color = SimbanyuTheme.colors.onPrimary
            )


            SimbanyuButton(
                modifier = Modifier.fillMaxWidth(),
                text = "Bayar Sekarang",
                backgroundColor = SimbanyuTheme.colors.onPrimary,
                textColor = SimbanyuTheme.colors.primary,
                iconRight = {
                    Icon(
                        painter = painterResource(R.drawable.ic_arrow_forward),
                        contentDescription = null,
                        tint = SimbanyuTheme.colors.primary
                    )
                }
            ) {

            }
        }

        Spacer(
            modifier = Modifier.height(SimbanyuTheme.spacing.large)
        )

        SimbanyuText(
            modifier = Modifier.padding(
                SimbanyuTheme.padding.small
            ),
            text = "Pemberitahuan Terbaru",
            style = SimbanyuTheme.typography.body,
        )


        SimbanyuText(
            modifier = Modifier.padding(
                SimbanyuTheme.padding.small
            ),
            text = "Penggunaan Air",
            style = SimbanyuTheme.typography.body,
        )
    }
}

@Preview(showBackground = true, device = Devices.PIXEL_7, showSystemUi = false)
@Composable
fun DashboardPreview() {
    SimbanyuTheme {
        DashboardScreen()
    }
}
