package com.avivba.simbanyu.ui.screens.usagehistory

import androidx.compose.animation.core.EaseInOutCubic
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.Icon
import androidx.compose.material3.pulltorefresh.PullToRefreshBox
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Devices
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.avivba.simbanyu.R
import com.avivba.simbanyu.ui.components.emptystate.EmptyState
import com.avivba.simbanyu.ui.components.item.UsageHistoryItem
import com.avivba.simbanyu.ui.components.loadingbox.LoadingBox
import com.avivba.simbanyu.ui.components.text.SimbanyuText
import com.avivba.simbanyu.ui.shared.Screen
import com.avivba.simbanyu.ui.shared.model.UiUsage
import com.avivba.simbanyu.ui.theme.SimbanyuTheme
import com.avivba.simbanyu.ui.utils.ObserveAsEvent
import ir.ehsannarmani.compose_charts.LineChart
import ir.ehsannarmani.compose_charts.models.DividerProperties
import ir.ehsannarmani.compose_charts.models.DotProperties
import ir.ehsannarmani.compose_charts.models.DrawStyle
import ir.ehsannarmani.compose_charts.models.GridProperties
import ir.ehsannarmani.compose_charts.models.HorizontalIndicatorProperties
import ir.ehsannarmani.compose_charts.models.IndicatorCount
import ir.ehsannarmani.compose_charts.models.LabelHelperProperties
import ir.ehsannarmani.compose_charts.models.LabelProperties
import ir.ehsannarmani.compose_charts.models.Line
import ir.ehsannarmani.compose_charts.models.LineProperties
import org.koin.compose.viewmodel.koinViewModel
import java.time.Instant


@Composable
fun UsageHistoryScreen(
    modifier: Modifier = Modifier,
    viewModel: UsageHistoryViewModel = koinViewModel(),
    onNavigate: (Screen) -> Unit = {}
) {
    val state by viewModel.state.collectAsStateWithLifecycle()

    ObserveAsEvent(viewModel.event) {
    }

    LoadingBox(
        modifier = modifier,
        isLoading = state.isLoading
    ) {
        PullToRefreshBox(
            modifier = Modifier,
            isRefreshing = state.isRefreshing,
            onRefresh = {
                viewModel.onAction(UsageHistoryAction.Refresh)
            }
        ) {
            UsageHistoryContent(
                state = state,
                onAction = viewModel::onAction
            )
        }
    }
}

@Composable
fun UsageHistoryContent(
    modifier: Modifier = Modifier,
    state: UsageHistoryUiState,
    onAction: (UsageHistoryAction) -> Unit = {},
) {
    var isExpanded by remember { mutableStateOf(false) }

    val chartColor = SolidColor(SimbanyuTheme.colors.brand)
    val chartGradientColor = SimbanyuTheme.colors.brandLighter
    val usageData = remember(state.usages, chartColor, chartGradientColor) {
        listOf(
            Line(
                label = "Penggunaan",
                values = state.usages.reversed().map { it.amount.toDouble() },
                color = chartColor,
                firstGradientFillColor = chartGradientColor,
                secondGradientFillColor = Color.Transparent,
                strokeAnimationSpec = tween(2000, easing = EaseInOutCubic),
                gradientAnimationDelay = 1000,
                drawStyle = DrawStyle.Stroke(width = 2.dp),
            )
        )
    }

    Column(
        modifier = modifier
            .fillMaxSize(),
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(SimbanyuTheme.padding.large),
            verticalAlignment = Alignment.CenterVertically
        ) {
            SimbanyuText(
                modifier = Modifier.weight(1f),
                text = "Riwayat Penggunaan",
                style = SimbanyuTheme.typography.displayMedium
            )

            Box(
                modifier = Modifier
            ) {
                Row(
                    modifier = Modifier
                        .background(
                            color = SimbanyuTheme.colors.surfaceCardAlternate,
                            shape = SimbanyuTheme.shapes.medium
                        )
                        .clip(
                            SimbanyuTheme.shapes.medium
                        )
                        .clickable {
                            isExpanded = true
                        }
                        .padding(SimbanyuTheme.padding.medium),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    SimbanyuText(
                        modifier = Modifier.padding(end = SimbanyuTheme.padding.small),
                        text = if (state.selectedYear > 0) state.selectedYear.toString() else "Tahun",
                        style = SimbanyuTheme.typography.labelAction
                    )
                    Icon(
                        painter = painterResource(if (isExpanded) R.drawable.ic_arrow_dropdown_up else R.drawable.ic_arrow_dropdown_down),
                        contentDescription = null,
                    )
                }

                DropdownMenu(
                    expanded = isExpanded,
                    onDismissRequest = {
                        isExpanded = false
                    },
                    containerColor = SimbanyuTheme.colors.surfaceCard,
                ) {
                    state.years.forEach { year ->
                        DropdownMenuItem(
                            text = {
                                SimbanyuText(
                                    text = year.toString(),
                                    style = SimbanyuTheme.typography.bodyStandard
                                )
                            },
                            onClick = {
                                onAction(UsageHistoryAction.Filter(year))
                                isExpanded = false
                            }
                        )
                    }
                }
            }
        }

        if (state.usages.isNotEmpty()) {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .verticalScroll(rememberScrollState())
                    .testTag("usage_history_list")
                    .padding(
                        horizontal = SimbanyuTheme.padding.large
                    )
                    .padding(
                        bottom = SimbanyuTheme.padding.large
                    ),
                verticalArrangement = Arrangement.spacedBy(SimbanyuTheme.spacing.medium),
            ) {
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
                    LineChart(
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(150.dp),
                        data = usageData,
                        dotsProperties = DotProperties(
                            enabled = true,
                            color = SolidColor(SimbanyuTheme.colors.brandText),
                            strokeColor = SolidColor(SimbanyuTheme.colors.brand),
                            strokeWidth = 2.dp,
                            radius = 3.dp
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
                            enabled = false,
                        )
                    )
                }

                Row(
                    modifier = Modifier
                        .fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(
                        SimbanyuTheme.spacing.medium
                    )
                ) {
                    Column(
                        modifier = Modifier
                            .weight(1f)
                            .background(
                                brush = Brush.horizontalGradient(
                                    listOf(
                                        SimbanyuTheme.colors.brand,
                                        SimbanyuTheme.colors.brandLighter
                                    )
                                ),
                                shape = SimbanyuTheme.shapes.large
                            )
                            .padding(
                                SimbanyuTheme.padding.large
                            ),
                    ) {
                        SimbanyuText(
                            text = "TOTAL PENGGUNAAN",
                            style = SimbanyuTheme.typography.labelBadge,
                            color = SimbanyuTheme.colors.brandText
                        )

                        SimbanyuText(
                            text = "${state.usageTotal} m³",
                            style = SimbanyuTheme.typography.displayMedium,
                            color = SimbanyuTheme.colors.brandText
                        )
                    }

                    Column(
                        modifier = Modifier
                            .weight(1f)
                            .background(
                                brush = Brush.horizontalGradient(
                                    listOf(
                                        SimbanyuTheme.colors.brand,
                                        SimbanyuTheme.colors.brandLighter
                                    )
                                ),
                                shape = SimbanyuTheme.shapes.large
                            )
                            .padding(
                                SimbanyuTheme.padding.large
                            ),
                    ) {
                        SimbanyuText(
                            text = "RATA-RATA BULANAN",
                            style = SimbanyuTheme.typography.labelBadge,
                            color = SimbanyuTheme.colors.brandText
                        )

                        SimbanyuText(
                            text = "%.1f m³".format(state.usageAverage),
                            style = SimbanyuTheme.typography.displayMedium,
                            color = SimbanyuTheme.colors.brandText
                        )
                    }
                }

                state.usages.forEach { usage ->
                    UsageHistoryItem(
                        usage = usage,
                        onClick = {
                            onAction(UsageHistoryAction.ClickUsage(usage))
                        }
                    )
                }
            }
        } else {
            EmptyState(
                modifier = Modifier
                    .fillMaxSize(),
                message = "Anda belum memiliki riwayat penggunaan"
            )
        }
    }
}

@Preview(showBackground = true, device = Devices.PIXEL_7, showSystemUi = false)
@Composable
fun UsageHistoryPreview() {
    UsageHistoryContent(
        state = UsageHistoryUiState(
            usages = listOf(
                UiUsage(
                    id = "",
                    meterStart = 10,
                    meterEnd = 20,
                    amount = 10,
                    periodMonth = "Januari",
                    periodYear = 2026,
                    realDate = Instant.now()
                )
            ),
            usageTotal = 103,
            usageAverage = 23.5
        )
    )
}