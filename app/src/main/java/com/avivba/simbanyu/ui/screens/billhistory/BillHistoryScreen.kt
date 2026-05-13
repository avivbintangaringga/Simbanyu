package com.avivba.simbanyu.ui.screens.billhistory

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
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
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Devices
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.avivba.simbanyu.R
import com.avivba.simbanyu.ui.components.buttons.SimbanyuButton
import com.avivba.simbanyu.ui.components.emptystate.EmptyState
import com.avivba.simbanyu.ui.components.item.BillHistoryItem
import com.avivba.simbanyu.ui.components.loadingbox.LoadingBox
import com.avivba.simbanyu.ui.components.text.SimbanyuText
import com.avivba.simbanyu.ui.shared.Screen
import com.avivba.simbanyu.ui.shared.model.UiBillingStatus
import com.avivba.simbanyu.ui.theme.SimbanyuTheme
import com.avivba.simbanyu.ui.utils.ObserveAsEvent
import com.avivba.simbanyu.ui.utils.toDottedCurrency
import org.koin.compose.viewmodel.koinViewModel


@Composable
fun BillHistoryScreen(
    modifier: Modifier = Modifier,
    viewModel: BillHistoryViewModel = koinViewModel(),
    onNavigate: (Screen) -> Unit = {}
) {
    val state by viewModel.state.collectAsStateWithLifecycle()

    ObserveAsEvent(viewModel.event) {
        when (it) {
            is BillHistoryEvent.ShowBill -> {
                onNavigate(Screen.BillDetails(it.bill.id))
            }

            is BillHistoryEvent.PayBill -> {
                onNavigate(Screen.Invoice(it.billIds))
            }
        }
    }

    LoadingBox(
        modifier = modifier,
        isLoading = state.isLoading
    ) {
        PullToRefreshBox(
            modifier = Modifier,
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
                text = "Riwayat Tagihan",
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
                                onAction(BillHistoryAction.Filter(year))
                                isExpanded = false
                            }
                        )
                    }
                }
            }
        }

        if (state.bills.isNotEmpty()) {
            Column(
                modifier = Modifier
                    .weight(1f)
                    .verticalScroll(rememberScrollState())
                    .padding(
                        horizontal = SimbanyuTheme.padding.large
                    )
                    .padding(
                        bottom = SimbanyuTheme.padding.large
                    ),
                verticalArrangement = Arrangement.spacedBy(SimbanyuTheme.spacing.medium),
            ) {
                state.bills.forEach { bill ->
                    var iconId: Int
                    var iconTint: Color
                    var iconBackgroundColor: Color
                    var statusBackgroundColor: Color

                    if (bill.status == UiBillingStatus.PAID) {
                        iconId = R.drawable.ic_done_all
                        iconTint = SimbanyuTheme.colors.statusPositiveText
                        iconBackgroundColor = SimbanyuTheme.colors.statusPositive
                        statusBackgroundColor = SimbanyuTheme.colors.statusPositiveText
                    } else if (bill.status == UiBillingStatus.PENDING) {
                        iconId = R.drawable.ic_pending
                        iconTint = SimbanyuTheme.colors.statusWarningText
                        iconBackgroundColor = SimbanyuTheme.colors.statusWarning
                        statusBackgroundColor = SimbanyuTheme.colors.statusWarningText
                    } else {
                        statusBackgroundColor = SimbanyuTheme.colors.statusNegativeText

                        if (state.isMultipleSelectMode) {
                            if (bill.isSelected) {
                                iconId = R.drawable.ic_check_circle
                                iconTint = SimbanyuTheme.colors.statusPositiveText
                                iconBackgroundColor = SimbanyuTheme.colors.statusPositive
                            } else {
                                iconId = R.drawable.ic_circle
                                iconTint = SimbanyuTheme.colors.statusNeutralText
                                iconBackgroundColor = SimbanyuTheme.colors.statusNeutral
                            }
                        } else {
                            iconId = R.drawable.ic_contract
                            iconTint = SimbanyuTheme.colors.statusNegativeText
                            iconBackgroundColor = SimbanyuTheme.colors.statusNegative
                        }
                    }

                    val isDisabled = if (state.isMultipleSelectMode) {
                        bill.status == UiBillingStatus.PAID || bill.status == UiBillingStatus.PENDING
                    } else {
                        false
                    }

                    BillHistoryItem(
                        period = "${bill.periodMonth} ${bill.periodYear}",
                        amount = bill.amount,
                        status = bill.status.status,
                        iconId = iconId,
                        iconTint = iconTint,
                        iconBackgroundColor = iconBackgroundColor,
                        statusBackgroundColor = statusBackgroundColor,
                        isDisabled = isDisabled,
                        onClick = {
                            onAction(BillHistoryAction.ClickBill(bill))
                        },
                        onLongClick = {
                            onAction(BillHistoryAction.LongClickBill(bill))
                        }
                    )
                }
            }

            if (state.isMultipleSelectMode) {
                Row(
                    modifier = Modifier
                        .padding(
                            horizontal = SimbanyuTheme.padding.large,
                            vertical = SimbanyuTheme.padding.medium
                        )
                        .fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    SimbanyuText(
                        modifier = Modifier.weight(1f),
                        text = state.totalSelectedBills.toDottedCurrency(),
                        style = SimbanyuTheme.typography.displaySmall,
                        color = SimbanyuTheme.colors.brand
                    )
                    SimbanyuButton(
                        text = "Bayar tagihan",
                        modifier = Modifier
                            .background(
                                brush = SimbanyuTheme.colors.brush.brandVertical,
                            ),
                        iconRight = {
                            Icon(
                                painter = painterResource(R.drawable.ic_arrow_forward),
                                contentDescription = null,
                                tint = SimbanyuTheme.colors.actionBrandText
                            )
                        },
                        onClick = {
                            onAction(BillHistoryAction.PayMultipleBills)
                        }
                    )
                }
            }
        } else {
            EmptyState(
                modifier = Modifier.weight(1f),
                message = "Anda belum memiliki riwayat tagihan"
            )
        }
    }
}

@Preview(showBackground = true, device = Devices.PIXEL_7, showSystemUi = false)
@Composable
private fun BillHistoryPreview() {
    BillHistoryContent(
        state = BillHistoryUiState(
            bills = fakeBills,
            isMultipleSelectMode = true
        )
    )
}