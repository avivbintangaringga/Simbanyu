package com.avivba.simbanyu.ui.screens.billhistory

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
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
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.avivba.simbanyu.R
import com.avivba.simbanyu.ui.components.buttons.SimbanyuCardButton
import com.avivba.simbanyu.ui.components.item.BillHistoryItem
import com.avivba.simbanyu.ui.components.text.SimbanyuText
import com.avivba.simbanyu.ui.screens.dashboard.DashboardAction
import com.avivba.simbanyu.ui.screens.dashboard.DashboardContent
import com.avivba.simbanyu.ui.screens.main.Screen
import com.avivba.simbanyu.ui.theme.SimbanyuTheme
import com.avivba.simbanyu.ui.utils.ObserveAsEvent
import org.koin.compose.viewmodel.koinViewModel


@Composable
fun BillHistoryScreen(
    viewModel: BillHistoryViewModel = koinViewModel(),
    onNavigate: (Screen) -> Unit = {}
) {
    val state by viewModel.state.collectAsStateWithLifecycle()

    ObserveAsEvent(viewModel.event) {
        when (it) {
            is BillHistoryEvent.ShowBill -> {
                onNavigate(Screen.BillDetails(it.bill.id))
            }
        }
    }

    PullToRefreshBox(
        isRefreshing = state.isRefreshing,
        onRefresh = {
            viewModel.onAction(BillHistoryAction.Refresh)
        }
    ) {
        BillHistoryContent(
            state = state,
            onNavigate = onNavigate,
            onAction = viewModel::onAction
        )
    }

}

@Composable
fun BillHistoryContent(
    modifier: Modifier = Modifier,
    state: BillHistoryUiState,
    onAction: (BillHistoryAction) -> Unit = {},
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
                text = "Riwayat Tagihan",
                style = SimbanyuTheme.typography.displayLarge
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
                                onAction(BillHistoryAction.Filter(year))
                                isExpanded = false
                            }
                        )
                    }
                }
            }
        }

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
                                SimbanyuTheme.colors.statusPositiveText
                            )
                        ),
                        shape = SimbanyuTheme.shapes.large
                    )
                    .padding(
                        SimbanyuTheme.padding.large
                    ),
            ) {
                SimbanyuText(
                    text = "TELAH DIBAYAR",
                    style = SimbanyuTheme.typography.labelBadge,
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
                        text = state.totalPaid,
                        style = SimbanyuTheme.typography.displayMedium,
                        color = SimbanyuTheme.colors.brandText
                    )
                }
            }

            Column(
                modifier = Modifier
                    .weight(1f)
                    .background(
                        brush = Brush.horizontalGradient(
                            listOf(
                                SimbanyuTheme.colors.brand,
                                SimbanyuTheme.colors.statusNegativeText
                            )
                        ),
                        shape = SimbanyuTheme.shapes.large
                    )
                    .padding(
                        SimbanyuTheme.padding.large
                    ),
            ) {
                SimbanyuText(
                    text = "BELUM DIBAYAR",
                    style = SimbanyuTheme.typography.labelBadge,
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
                        text = state.totalUnpaid,
                        style = SimbanyuTheme.typography.displayMedium,
                        color = SimbanyuTheme.colors.brandText
                    )
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
            items(
                items = state.bills,
                key = { it.id }
            ) { bill ->
                BillHistoryItem(
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
fun BillHistoryPreview() {
    BillHistoryContent(
        state = BillHistoryUiState(
            bills = fakeBills,
            totalPaid = "100.000",
            totalUnpaid = "150.000"
        )
    )
}