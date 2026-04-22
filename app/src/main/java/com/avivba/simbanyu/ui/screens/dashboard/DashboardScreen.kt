package com.avivba.simbanyu.ui.screens.dashboard

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Icon
import androidx.compose.material3.pulltorefresh.PullToRefreshBox
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Devices
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.avivba.simbanyu.R
import com.avivba.simbanyu.ui.components.buttons.SimbanyuButton
import com.avivba.simbanyu.ui.components.item.AnnouncementItem
import com.avivba.simbanyu.ui.components.text.SimbanyuText
import com.avivba.simbanyu.ui.shared.Screen
import com.avivba.simbanyu.ui.theme.SimbanyuTheme
import com.avivba.simbanyu.ui.utils.ObserveAsEvent
import ir.ehsannarmani.compose_charts.ColumnChart
import ir.ehsannarmani.compose_charts.models.BarProperties
import ir.ehsannarmani.compose_charts.models.Bars
import ir.ehsannarmani.compose_charts.models.DividerProperties
import ir.ehsannarmani.compose_charts.models.GridProperties
import ir.ehsannarmani.compose_charts.models.HorizontalIndicatorProperties
import ir.ehsannarmani.compose_charts.models.IndicatorCount
import ir.ehsannarmani.compose_charts.models.LabelHelperProperties
import ir.ehsannarmani.compose_charts.models.LabelProperties
import ir.ehsannarmani.compose_charts.models.LineProperties
import org.koin.compose.viewmodel.koinViewModel


@Composable
fun DashboardScreen(
    modifier: Modifier = Modifier,
    viewModel: DashboardViewModel = koinViewModel(),
    onNavigate: (Screen) -> Unit = {}
) {
    val state by viewModel.state.collectAsStateWithLifecycle()

    ObserveAsEvent(viewModel.event) {
        when (it) {
            DashboardEvent.ViewAllAnnouncements -> {
                onNavigate(Screen.AnnouncementScreen)
            }
        }
    }

    PullToRefreshBox(
        modifier = modifier,
        isRefreshing = state.isRefreshing,
        onRefresh = {
            viewModel.onAction(DashboardAction.Refresh)
        }
    ) {
        DashboardContent(
            state = state,
            onAction = viewModel::onAction,
            onNavigate = onNavigate
        )
    }
}


@Composable
fun DashboardContent(
    modifier: Modifier = Modifier,
    state: DashboardUiState,
    onAction: (DashboardAction) -> Unit = {},
    onNavigate: (Screen) -> Unit = {}
) {
    val scrollState = rememberScrollState()
    val barColor = SimbanyuTheme.colors.brush.brandVertical
    val barColorMax = Brush.verticalGradient(
        listOf(
            SimbanyuTheme.colors.statusWarningText,
            SimbanyuTheme.colors.brandLight
        )
    )

    val usageData = remember(state.waterUsage, barColor, barColorMax) {
        state.waterUsage.map { usage ->
            Bars(
                label = usage.month,
                values = listOf(
                    Bars.Data(
                        value = usage.usage.toDouble(),
                        color = if (usage.isMaximum) barColorMax else barColor
                    )
                )
            )
        }
    }

    Column(
        modifier = modifier
            .fillMaxSize()
            .verticalScroll(scrollState)
            .padding(SimbanyuTheme.padding.large),
        verticalArrangement = Arrangement.spacedBy(SimbanyuTheme.spacing.medium),
    ) {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(
                    color = SimbanyuTheme.colors.brand,
                    shape = SimbanyuTheme.shapes.large
                )
                .padding(
                    bottom = SimbanyuTheme.spacing.small
                )
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
                    text = "${state.name} 👋",
                    style = SimbanyuTheme.typography.displayMedium
                )
            }
        }

        if (state.bill.isNotEmpty()) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(
                        brush = Brush.horizontalGradient(
                            listOf(
                                SimbanyuTheme.colors.brand,
                                SimbanyuTheme.colors.statusWarningText,
                            )
                        ),
                        shape = SimbanyuTheme.shapes.extraLarge
                    )
                    .padding(SimbanyuTheme.padding.extraLarge),
                verticalArrangement = Arrangement.spacedBy(
                    SimbanyuTheme.spacing.medium
                )
            ) {
                SimbanyuText(
                    text = "ANDA MEMILIKI TAGIHAN BELUM DIBAYAR",
                    style = SimbanyuTheme.typography.bodySmall,
                    color = SimbanyuTheme.colors.brandText
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
                        modifier = Modifier.weight(1f),
                        text = state.bill,
                        style = SimbanyuTheme.typography.displayHero,
                        color = SimbanyuTheme.colors.brandText
                    )

                    Icon(
                        modifier = Modifier
                            .size(40.dp)
                            .padding(
                                end = SimbanyuTheme.padding.small
                            ),
                        painter = painterResource(R.drawable.ic_contract),
                        contentDescription = null,
                        tint = SimbanyuTheme.colors.brandText.copy(alpha = 0.3f)
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
                    iconLeft = {
                        Icon(
                            painter = painterResource(R.drawable.ic_payments_filled),
                            contentDescription = null,
                            tint = SimbanyuTheme.colors.actionBrand
                        )
                    },
//                    iconRight = {
//                        Icon(
//                            painter = painterResource(R.drawable.ic_arrow_forward),
//                            contentDescription = null,
//                            tint = SimbanyuTheme.colors.actionBrand
//                        )
//                    }
                ) {
                    onAction(DashboardAction.PayBill)
                }
            }
        } else {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(
                        brush = Brush.horizontalGradient(
                            listOf(
                                SimbanyuTheme.colors.brand,
                                SimbanyuTheme.colors.statusPositiveText
                            )
                        ),
                        shape = SimbanyuTheme.shapes.extraLarge
                    )
                    .padding(SimbanyuTheme.padding.extraLarge),
                verticalArrangement = Arrangement.spacedBy(
                    SimbanyuTheme.spacing.medium
                )
            ) {
                SimbanyuText(
                    text = "Anda tidak memiliki tagihan \uD83C\uDF89",
                    style = SimbanyuTheme.typography.displayMedium,
                    color = SimbanyuTheme.colors.brandText
                )
            }
        }

        Spacer(
            modifier = Modifier.height(SimbanyuTheme.spacing.extraSmall)
        )

        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically
        ) {
            SimbanyuText(
                modifier = Modifier
                    .padding(
                        SimbanyuTheme.padding.small
                    )
                    .weight(1f),
                text = "Penggunaan Air",
                style = SimbanyuTheme.typography.titleSection,
            )
            SimbanyuText(
                text = "6 bulan terakhir",
                style = SimbanyuTheme.typography.bodyProminent,
                color = SimbanyuTheme.colors.brand
            )
        }

        Column(
            modifier = Modifier
                .fillMaxWidth()
                .border(
                    1.dp,
                    SimbanyuTheme.colors.borderCard,
                    shape = SimbanyuTheme.shapes.extraLarge
                )
                .background(
                    color = SimbanyuTheme.colors.surfaceCard,
                    shape = SimbanyuTheme.shapes.extraLarge
                )
                .padding(SimbanyuTheme.padding.large)
        ) {
            ColumnChart(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(150.dp),
                data = usageData,
                barProperties = BarProperties(
                    cornerRadius = Bars.Data.Radius.Rectangle(
                        topLeft = 8.dp,
                        topRight = 8.dp
                    ),
                    thickness = 24.dp
                ),
                gridProperties = GridProperties(
                    enabled = true,
                    yAxisProperties = GridProperties.AxisProperties(
                        enabled = false
                    ),
                    xAxisProperties = GridProperties.AxisProperties(
                        enabled = true,
                        lineCount = 3
                    )
                ),
                labelHelperProperties = LabelHelperProperties(
                    enabled = false
                ),
                indicatorProperties = HorizontalIndicatorProperties(
                    count = IndicatorCount.CountBased(3)
                ),
                dividerProperties = DividerProperties(
                    yAxisProperties = LineProperties(
                        enabled = false
                    )
                ),
                labelProperties = LabelProperties(
                    enabled = true,
                    textStyle = SimbanyuTheme.typography.bodySmall
                )
            )

            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(
                        top = SimbanyuTheme.padding.large
                    )
            ) {
                SimbanyuText(
                    modifier = Modifier.weight(1f),
                    text = "RATA-RATA",
                    style = SimbanyuTheme.typography.labelBadge
                )

                SimbanyuText(
                    text = "PUNCAK (${state.waterUsageMaximumMonth})",
                    style = SimbanyuTheme.typography.labelBadge,
                )
            }

            Row(
                modifier = Modifier
                    .fillMaxWidth()
            ) {
                SimbanyuText(
                    modifier = Modifier.weight(1f),
                    text = "%.1f m³".format(state.waterUsageAverage),
                    style = SimbanyuTheme.typography.displayMedium,
                    color = SimbanyuTheme.colors.brand
                )

                SimbanyuText(
                    text = "${state.waterUsageMaximum} m³",
                    style = SimbanyuTheme.typography.displayMedium,
                    color = SimbanyuTheme.colors.statusWarningText
                )
            }
        }

//        Row(
//            modifier = Modifier
//                .height(IntrinsicSize.Max)
//                .fillMaxWidth(),
//            horizontalArrangement = Arrangement.spacedBy(SimbanyuTheme.spacing.large)
//        ) {
//            SimbanyuCardButton(
//                modifier = Modifier
//                    .weight(1f),
//                title = "Riwayat",
//                subTitle = "Lihat riwayat penggunaan",
//                iconId = R.drawable.ic_history,
//                onClick = {
//                    // TODO: handle onclick
//                }
//            )
//
//            SimbanyuCardButton(
//                modifier = Modifier
//                    .weight(1f),
//                title = "Lapor",
//                subTitle = "Jika terjadi gangguan",
//                iconId = R.drawable.ic_report,
//                onClick = {
//                    // TODO: handle onclick
//                }
//            )
//        }

        SimbanyuText(
            modifier = Modifier.padding(
                SimbanyuTheme.padding.small
            ),
            text = "Pemberitahuan Terbaru",
            style = SimbanyuTheme.typography.titleSection,
        )

        Column(
            modifier = Modifier
                .fillMaxWidth(),
            verticalArrangement = Arrangement.spacedBy(
                SimbanyuTheme.spacing.medium
            ),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            state.announcements.forEach { announcement ->
                AnnouncementItem(
                    title = announcement.title,
                    content = announcement.content,
                    time = announcement.time,
                    iconId = R.drawable.ic_campaign_filled,
                    iconTint = SimbanyuTheme.colors.brand
                )
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
                        painter = painterResource(R.drawable.ic_chevron_forward),
                        contentDescription = null,
                        tint = SimbanyuTheme.colors.actionBrand
                    )
                }
            ) {
                onAction(DashboardAction.ViewAllAnnouncements)
            }
        }

    }
}

@Preview(showBackground = true, device = Devices.PIXEL_7, showSystemUi = false)
@Composable
private fun DashboardPreview() {
    SimbanyuTheme {
        DashboardContent(
            state = DashboardUiState(
                waterUsage = fakeWaterUsage,
                announcements = fakeAnnouncements,
                bill = "125.000"
            )
        )
    }
}
