package com.avivba.simbanyu.ui.screens.dashboard

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Card
import androidx.compose.material3.CardColors
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.LineHeightStyle
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Devices
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.avivba.simbanyu.R
import com.avivba.simbanyu.ui.components.buttons.SimbanyuButton
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
                    SimbanyuTheme.colors.surfaceCardAlternate,
                    SimbanyuTheme.shapes.large
                )
                .padding(
                    SimbanyuTheme.padding.large
                )
        ) {
            SimbanyuText(
                text = "SELAMAT DATANG",
                style = SimbanyuTheme.typography.labelOverline,
            )

            SimbanyuText(
                text = "Budi Budiman 👋",
                style = SimbanyuTheme.typography.displayMedium
            )
        }

        Column(
            modifier = Modifier
                .fillMaxWidth()
                .background(
                    brush = SimbanyuTheme.colors.brush.brandHorizontal,
                    shape = SimbanyuTheme.shapes.extraLarge
                )
                .padding(SimbanyuTheme.padding.extraLarge),
            verticalArrangement = Arrangement.spacedBy(
                SimbanyuTheme.spacing.medium
            )
        ) {

            SimbanyuText(
                text = "TAGIHAN BELUM DIBAYAR\nPeriode Januari 2026",
                style = SimbanyuTheme.typography.bodySmall,
                color = SimbanyuTheme.colors.brandText
            )

            Spacer(
                modifier = Modifier.height(SimbanyuTheme.spacing.small)
            )

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.Start,
                verticalAlignment = Alignment.CenterVertically
            ) {
                SimbanyuText(
                    modifier = Modifier,
                    text = "Rp.",
                    color = SimbanyuTheme.colors.brandText,
                    style = SimbanyuTheme.typography.titleSection
                )

                SimbanyuText(
                    text = "153.000",
                    style = SimbanyuTheme.typography.displayHero,
                    color = SimbanyuTheme.colors.brandText
                )
            }

            Spacer(
                modifier = Modifier.height(SimbanyuTheme.spacing.small)
            )

            SimbanyuButton(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(
                        color = SimbanyuTheme.colors.actionBrandText,
                        shape = SimbanyuTheme.shapes.extraLarge
                    ),
                text = "Bayar Sekarang",
                textColor = SimbanyuTheme.colors.actionBrand,
                iconRight = {
                    Icon(
                        painter = painterResource(R.drawable.ic_arrow_forward),
                        contentDescription = null,
                        tint = SimbanyuTheme.colors.actionBrand
                    )
                }
            ) {

            }
        }

        Spacer(
            modifier = Modifier.height(SimbanyuTheme.spacing.extraSmall)
        )

        SimbanyuText(
            modifier = Modifier.padding(
                SimbanyuTheme.padding.small
            ),
            text = "Pemberitahuan Terbaru",
            style = SimbanyuTheme.typography.titleSection,
        )

        Column(
            modifier = Modifier,
            verticalArrangement = Arrangement.spacedBy(
                SimbanyuTheme.spacing.medium
            ),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            repeat(3) {
                Card(
                    modifier = Modifier,
                    shape = SimbanyuTheme.shapes.extraLarge,
                    colors = CardColors(
                        containerColor = SimbanyuTheme.colors.surfaceCardAlternate,
                        contentColor = SimbanyuTheme.colors.textBody,
                        disabledContainerColor = SimbanyuTheme.colors.surfaceCardAlternate,
                        disabledContentColor = SimbanyuTheme.colors.textBody
                    ),
                    border = BorderStroke(
                        width = 1.dp,
                        color = SimbanyuTheme.colors.borderCardAlternate
                    )
                ) {
                    Row(
                        modifier = Modifier
                            .padding(
                                SimbanyuTheme.padding.medium.plus(
                                    SimbanyuTheme.padding.small
                                )
                            )
                            .fillMaxWidth(),
                        horizontalArrangement = Arrangement.spacedBy(
                            SimbanyuTheme.spacing.large
                        ),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Icon(
                            modifier = Modifier
                                .background(
                                    SimbanyuTheme.colors.brandText,
                                    shape = CircleShape
                                )
                                .padding(
                                    SimbanyuTheme.padding.large
                                ),
                            painter = painterResource(R.drawable.ic_error),
                            contentDescription = null
                        )
                        Column(
                            modifier = Modifier
                                .fillMaxWidth()
                        ) {
                            Row(
                                Modifier.fillMaxWidth(),
                            ) {
                                SimbanyuText(
                                    modifier = Modifier.weight(1f),
                                    text = "Layanan normal",
                                    style = SimbanyuTheme.typography.titleSection,
                                )
                                SimbanyuText(
                                    modifier = Modifier.padding(
                                        horizontal = SimbanyuTheme.padding.medium
                                    ),
                                    text = "5m lalu",
                                    textAlign = TextAlign.End,
                                    fontStyle = FontStyle.Italic,
                                    fontSize = 11.sp,
                                    color = SimbanyuTheme.colors.textInputDisabled
                                )
                            }

                            Spacer(
                                modifier = Modifier.height(
                                    SimbanyuTheme.spacing.small
                                )
                            )

                            SimbanyuText("Aliran air di wilayah anda terpantau stabil")
                        }
                    }
                }
            }

            SimbanyuButton(
                text = "Lihat semua",
                textColor = SimbanyuTheme.colors.actionBrand,
                modifier = Modifier.border(
                    1.dp,
                    SimbanyuTheme.colors.actionBrand,
                    shape = SimbanyuTheme.shapes.extraLarge
                ),
                iconRight = {
                    Icon(
                        painter = painterResource(R.drawable.ic_arrow_forward),
                        contentDescription = null,
                        tint = SimbanyuTheme.colors.actionBrand
                    )
                }
            ) { }
        }

        SimbanyuText(
            modifier = Modifier.padding(
                SimbanyuTheme.padding.small
            ),
            text = "Penggunaan Air",
            style = SimbanyuTheme.typography.titleSection,
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
