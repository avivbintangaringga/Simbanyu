package com.avivba.simbanyu.ui.screens.usagehistory

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.material3.pulltorefresh.PullToRefreshBox
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Devices
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.avivba.simbanyu.R
import com.avivba.simbanyu.ui.components.item.BillHistoryItem
import com.avivba.simbanyu.ui.components.item.UsageHistoryItem
import com.avivba.simbanyu.ui.components.text.SimbanyuText
import com.avivba.simbanyu.ui.screens.main.Screen
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
fun UsageHistoryScreen(
    viewModel: UsageHistoryViewModel = koinViewModel(),
    onNavigate: (Screen) -> Unit = {}
) {
    val state by viewModel.state.collectAsStateWithLifecycle()

    ObserveAsEvent(viewModel.event) {
        when (it) {
            is UsageHistoryEvent.ShowUsage -> {
                onNavigate(Screen.BillDetails(it.usage.id))
            }
        }
    }

    PullToRefreshBox(
        isRefreshing = state.isRefreshing,
        onRefresh = {
            viewModel.onAction(UsageHistoryAction.Refresh)
        }
    ) {
        UsageHistoryContent(
            state = state,
            onNavigate = onNavigate,
            onAction = viewModel::onAction
        )
    }

}

@Composable
fun UsageHistoryContent(
    modifier: Modifier = Modifier,
    state: UsageHistoryUiState,
    onAction: (UsageHistoryAction) -> Unit = {},
    onNavigate: (Screen) -> Unit = {}
) {
    var isExpanded by remember { mutableStateOf(false) }

    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(SimbanyuTheme.padding.large),
        verticalArrangement = Arrangement.spacedBy(SimbanyuTheme.spacing.medium),
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = SimbanyuTheme.padding.large),
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

        LazyColumn(
            modifier = Modifier
                .fillMaxSize(),
            verticalArrangement = Arrangement.spacedBy(
                SimbanyuTheme.spacing.medium
            )
        ) {
            item {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .border(
                            2.dp,
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
                        data = emptyList(),
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
                }
            }
            item {
                Row(
                    modifier = Modifier
                        .height(IntrinsicSize.Max)
                        .fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(SimbanyuTheme.spacing.medium)
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
                            text = state.totalPaid,
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
                            text = state.totalUnpaid,
                            style = SimbanyuTheme.typography.displayMedium,
                            color = SimbanyuTheme.colors.brandText
                        )
                    }
                }
            }

            items(
                items = state.usages.sortedByDescending { it.id },
                key = { it.id }
            ) { bill ->
                UsageHistoryItem(
                    period = "${bill.periodMonth} ${bill.periodYear}",
                    id = bill.id,
                    amount = bill.amount,
                    status = bill.status.status,
                    iconId = R.drawable.ic_history
                )
            }
        }
    }
}

@Preview(showBackground = true, device = Devices.PIXEL_7, showSystemUi = false)
@Composable
fun UsageHistoryPreview() {
    UsageHistoryContent(
        state = UsageHistoryUiState(
            usages = fakeUsages,
            totalPaid = "100.000",
            totalUnpaid = "150.000"
        )
    )
}