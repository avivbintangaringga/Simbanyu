package com.avivba.simbanyu.ui.screens.operator.subscriberdetail

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import com.avivba.simbanyu.R
import com.avivba.simbanyu.ui.components.item.BillHistoryItem
import com.avivba.simbanyu.ui.components.text.SimbanyuText
import com.avivba.simbanyu.ui.shared.model.BillingStatus
import com.avivba.simbanyu.ui.theme.SimbanyuTheme

@Composable
internal fun BillHistory(
    modifier: Modifier = Modifier,
    state: SubscriberDetailsUiState,
    onAction: (SubscriberDetailsAction) -> Unit = {}
) {

    var isExpanded by remember { mutableStateOf(false) }
    val listState = rememberLazyListState()

    Column(
        modifier = modifier
            .fillMaxSize(),
        verticalArrangement = Arrangement.spacedBy(SimbanyuTheme.spacing.medium),
        horizontalAlignment = Alignment.End
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            SimbanyuText(
                text = "Tagihan Pelanggan",
                style = SimbanyuTheme.typography.titleSection
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
                        text = if (state.billSelectedYear > 0) state.billSelectedYear.toString() else "Tahun",
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
                    state.billYears.forEach { year ->
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
        }

        LazyColumn(
            modifier = Modifier
                .weight(1f)
                .fillMaxSize(),
            verticalArrangement = Arrangement.spacedBy(
                SimbanyuTheme.spacing.medium
            ),
            state = listState,
        ) {
            items(
                items = state.bills.sortedByDescending { it.id },
                key = { it.id }
            ) { bill ->
                var iconId = R.drawable.ic_history
                var iconTint = SimbanyuTheme.colors.brand
                var iconBackgroundColor = SimbanyuTheme.colors.surfaceCardAlternate
                var statusBackgroundColor = SimbanyuTheme.colors.brand

                if (bill.status == BillingStatus.PAID) {
                    iconId = R.drawable.ic_done_all
                    iconTint = SimbanyuTheme.colors.statusPositiveText
                    iconBackgroundColor = SimbanyuTheme.colors.statusPositive
                    statusBackgroundColor = SimbanyuTheme.colors.statusPositiveText
                } else if (bill.status == BillingStatus.PENDING) {
                    iconId = R.drawable.ic_pending
                    iconTint = SimbanyuTheme.colors.statusWarningText
                    iconBackgroundColor = SimbanyuTheme.colors.statusWarning
                    statusBackgroundColor = SimbanyuTheme.colors.statusWarningText
                } else {
                    statusBackgroundColor = SimbanyuTheme.colors.statusNegativeText
                    iconId = R.drawable.ic_contract
                    iconTint = SimbanyuTheme.colors.statusNegativeText
                    iconBackgroundColor = SimbanyuTheme.colors.statusNegative
                }

                BillHistoryItem(
                    period = "${bill.periodMonth} ${bill.periodYear}",
                    id = bill.id,
                    amount = bill.amount,
                    status = bill.status.status,
                    iconId = iconId,
                    iconTint = iconTint,
                    iconBackgroundColor = iconBackgroundColor,
                    statusBackgroundColor = statusBackgroundColor,
                    canPayByCash = bill.status == BillingStatus.UNPAID,
                    onClickPayByCash = {
                        onAction(SubscriberDetailsAction.PayByCash(bill))
                    }
                )
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
internal fun BillHistoryPreview() {
    BillHistory(
        state = fakeSubscriberDetailsUiState
    )
}