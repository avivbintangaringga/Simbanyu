package com.avivba.simbanyu.ui.screens.operator.subscriberdetail

import androidx.compose.animation.core.EaseInOutCubic
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SecondaryTabRow
import androidx.compose.material3.Tab
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.avivba.simbanyu.R
import com.avivba.simbanyu.ui.components.buttons.SimbanyuButton
import com.avivba.simbanyu.ui.components.item.UsageHistoryItem
import com.avivba.simbanyu.ui.components.text.SimbanyuText
import com.avivba.simbanyu.ui.components.topbar.TopBar
import com.avivba.simbanyu.ui.screens.operator.meterrecord.MeterRecordAction
import com.avivba.simbanyu.ui.screens.usagehistory.UsageHistoryAction
import com.avivba.simbanyu.ui.shared.Screen
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
import kotlinx.coroutines.launch
import org.koin.compose.viewmodel.koinViewModel
import kotlin.collections.map


// TODO: Implement cash payment here

@Composable
fun SubscriberDetailsScreen(
    modifier: Modifier = Modifier,
    viewModel: SubscriberDetailsViewModel = koinViewModel(),
    onNavigate: (Screen) -> Unit = {},
    onNavigateBack: () -> Unit = {}
) {
    val state by viewModel.state.collectAsStateWithLifecycle()

    ObserveAsEvent(viewModel.event) {
        when (it) {
            SubscriberDetailsEvent.NavigateBack -> {
                onNavigateBack()
            }

            is SubscriberDetailsEvent.GoToRecordMeter -> {
                onNavigate(Screen.RecordMeter(it.subscriberId))
            }
        }
    }

    SubscriberDetailsContent(
        modifier = modifier,
        state = state,
        onAction = viewModel::onAction,
        onNavigate = onNavigate
    )
}


@Composable
fun SubscriberDetailsContent(
    modifier: Modifier = Modifier,
    state: SubscriberDetailsUiState,
    onAction: (SubscriberDetailsAction) -> Unit = {},
    onNavigate: (Screen) -> Unit = {},
) {
    val scrollState = rememberScrollState()
    val tabs = listOf("Tagihan", "Penggunaan")
    val pagerState = rememberPagerState {
        tabs.size
    }
    val scope = rememberCoroutineScope()

    Scaffold(
        modifier = modifier,
        containerColor = Color.Transparent,
        topBar = {
            TopBar(
                text = "Detail Pelanggan",
                navigationIcon = {
                    Icon(
                        painter = painterResource(R.drawable.ic_arrow_backward),
                        contentDescription = null,
                        modifier = Modifier
                            .fillMaxHeight()
                            .padding(
                                vertical = SimbanyuTheme.padding.small,
                                horizontal = SimbanyuTheme.padding.medium
                            )
                            .clip(CircleShape)
                            .clickable {
                                onAction(SubscriberDetailsAction.NavigateBack)
                            }
                            .padding(
                                SimbanyuTheme.padding.large
                            )
                    )
                },
            )
        }
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .padding(
                    SimbanyuTheme.padding.large
                )
                .verticalScroll(scrollState),
            verticalArrangement = Arrangement.spacedBy(
                SimbanyuTheme.spacing.medium
            )
        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(
                    SimbanyuTheme.spacing.medium
                )
            ) {
                Icon(
                    modifier = Modifier
                        .size(40.dp)
                        .background(
                            color = SimbanyuTheme.colors.brand.copy(alpha = 0.3f),
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
                        SimbanyuTheme.spacing.medium
                    )
                ) {
                    SimbanyuText(
                        text = state.name,
                        style = SimbanyuTheme.typography.displayMedium,
                        color = SimbanyuTheme.colors.brand
                    )

                    Row(
                        modifier = Modifier
                            .fillMaxWidth(),
                        horizontalArrangement = Arrangement.spacedBy(
                            SimbanyuTheme.spacing.medium
                        )
                    ) {
                        SimbanyuText(
                            modifier = Modifier
                                .background(
                                    color = SimbanyuTheme.colors.brand,
                                    shape = SimbanyuTheme.shapes.medium
                                )
                                .padding(
                                    vertical = SimbanyuTheme.padding.extraSmall,
                                    horizontal = SimbanyuTheme.padding.medium
                                ),
                            text = "ID: ${state.meterId}",
                            style = SimbanyuTheme.typography.labelUnit,
                            color = SimbanyuTheme.colors.brandText
                        )

                        SimbanyuText(
                            modifier = Modifier
                                .background(
                                    color = SimbanyuTheme.colors.brand.copy(0.3f),
                                    shape = SimbanyuTheme.shapes.medium
                                )
                                .padding(
                                    vertical = SimbanyuTheme.padding.extraSmall,
                                    horizontal = SimbanyuTheme.padding.medium
                                ),
                            text = "GOL: ${state.category}",
                            style = SimbanyuTheme.typography.labelUnit,
                            color = SimbanyuTheme.colors.brand
                        )
                    }
                }
            }

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(
                    SimbanyuTheme.spacing.small
                )
            ) {
                Icon(
                    painter = painterResource(R.drawable.ic_location_on_filled),
                    contentDescription = null,
                    tint = SimbanyuTheme.colors.brand,
                    modifier = Modifier.size(16.dp)
                )

                SimbanyuText(
                    modifier = Modifier,
                    text = state.address,
                    style = SimbanyuTheme.typography.labelUnit,
                )
            }

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(
                    SimbanyuTheme.spacing.small
                )
            ) {
                Icon(
                    painter = painterResource(R.drawable.ic_call_filled),
                    contentDescription = null,
                    tint = SimbanyuTheme.colors.brand,
                    modifier = Modifier.size(16.dp)
                )

                SimbanyuText(
                    modifier = Modifier,
                    text = state.phone,
                    style = SimbanyuTheme.typography.labelUnit,
                )
            }

            SimbanyuButton(
                text = "Hubungi via WhatsApp",
                modifier = Modifier
                    .fillMaxWidth()
                    .background(
                        brush = Brush.verticalGradient(
                            colors = listOf(
                                SimbanyuTheme.colors.statusPositiveText,
                                SimbanyuTheme.colors.statusPositiveText.copy(
                                    alpha = 0.6f
                                )
                            )
                        ),
                    ),
                iconLeft = {
                    Icon(
                        painter = painterResource(R.drawable.ic_call_filled),
                        contentDescription = null,
                        tint = SimbanyuTheme.colors.actionBrandText
                    )
                },
                onClick = {
                    onAction(SubscriberDetailsAction.RecordMeter)
                }
            )

            if (state.isRecorded) {
                SimbanyuButton(
                    text = "Perbarui Meter",
                    modifier = Modifier
                        .fillMaxWidth()
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
                            painter = painterResource(R.drawable.ic_edit_filled),
                            contentDescription = null,
                            tint = SimbanyuTheme.colors.actionBrandText
                        )
                    },
                    onClick = {
                        onAction(SubscriberDetailsAction.RecordMeter)
                    }
                )
            } else {
                SimbanyuButton(
                    text = "Catat Meter",
                    modifier = Modifier
                        .fillMaxWidth()
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
                        onAction(SubscriberDetailsAction.RecordMeter)
                    }
                )
            }

            SecondaryTabRow(
                modifier = Modifier
                    .fillMaxWidth(),
                selectedTabIndex = pagerState.currentPage,
                containerColor = SimbanyuTheme.colors.brandText,
                indicator = {
                    Box(
                        modifier = Modifier
                            .tabIndicatorOffset(state.selectedTabIndex)
                            .height(4.dp)
                            .background(
                                color = SimbanyuTheme.colors.brand,
                                shape = SimbanyuTheme.shapes.medium
                            ),
                    )
                }
            ) {
                tabs.forEachIndexed { index, title ->
                    Tab(
                        selected = state.selectedTabIndex == index,
                        onClick = {
                            scope.launch {
                                pagerState.animateScrollToPage(index)
                            }

                            onAction(SubscriberDetailsAction.ChangeTab(index))
                        },
                        text = {
                            SimbanyuText(
                                text = title,
                            )
                        }
                    )
                }
            }

            HorizontalPager(
                state = pagerState,
                modifier = Modifier
                    .weight(1f)
            ) { page ->
                when (page) {
                    0 -> {
                        BillHistory(
                            modifier = Modifier
                                .fillMaxWidth(),
                            state = state,
                            onAction = onAction
                        )
                    }

                    1 -> {
                        UsageHistory(
                            modifier = Modifier
                                .fillMaxWidth(),
                            state = state,
                            onAction = onAction
                        )
                    }
                }
            }
        }
    }
}

@Composable
private fun BillHistory(
    modifier: Modifier = Modifier,
    state: SubscriberDetailsUiState,
    onAction: (SubscriberDetailsAction) -> Unit = {}
) {

}

@Composable
private fun UsageHistory(
    modifier: Modifier = Modifier,
    state: SubscriberDetailsUiState,
    onAction: (SubscriberDetailsAction) -> Unit = {}
) {
    var isExpanded by remember { mutableStateOf(false) }
    val listState = rememberLazyListState()
    val chartColor = SolidColor(SimbanyuTheme.colors.brand)
    val chartGradientColor = SimbanyuTheme.colors.brandLighter
    val usageData = remember(state.usages, chartColor, chartGradientColor) {
        listOf(
            Line(
                label = "Penggunaan",
                values = state.usages.map { it.amount.toDouble() },
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
        modifier = modifier,
        verticalArrangement = Arrangement.spacedBy(SimbanyuTheme.spacing.medium),
        horizontalAlignment = Alignment.End
    ) {
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
                    text = if (state.usageSelectedYear > 0) state.usageSelectedYear.toString() else "Tahun",
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
                state.usageYears.forEach { year ->
                    DropdownMenuItem(
                        text = {
                            SimbanyuText(
                                text = year.toString(),
                                style = SimbanyuTheme.typography.bodyStandard
                            )
                        },
                        onClick = {
                            onAction(SubscriberDetailsAction.FilterUsage(year))
                            isExpanded = false
                        }
                    )
                }
            }
        }

        LazyColumn(
            modifier = modifier
                .fillMaxSize(),
            state = listState,
            verticalArrangement = Arrangement.spacedBy(
                SimbanyuTheme.spacing.medium
            )
        ) {
            item {
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
            }

            items(
                items = state.usages.sortedByDescending { it.id },
                key = { it.id }
            ) { usage ->
                UsageHistoryItem(
                    period = "${usage.periodMonth} ${usage.periodYear}",
                    amount = usage.amount.toString(),
                    iconId = R.drawable.ic_water_droplet,
                    meterStart = usage.meterStart.toString(),
                    meterEnd = usage.meterEnd.toString()
                )
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun SubscriberDetailsContentPreview() {
    SubscriberDetailsContent(
        state = fakeSubscriberDetailsUiState.copy(
            selectedTabIndex = 1
        )
    )
}