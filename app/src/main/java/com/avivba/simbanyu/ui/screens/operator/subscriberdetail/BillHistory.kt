package com.avivba.simbanyu.ui.screens.operator.subscriberdetail

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.AlertDialog
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
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import com.avivba.simbanyu.R
import com.avivba.simbanyu.ui.components.buttons.SimbanyuButton
import com.avivba.simbanyu.ui.components.emptystate.EmptyState
import com.avivba.simbanyu.ui.components.item.BillHistoryItem
import com.avivba.simbanyu.ui.components.text.SimbanyuText
import com.avivba.simbanyu.ui.shared.model.UiBillingStatus
import com.avivba.simbanyu.ui.theme.SimbanyuTheme

@Composable
internal fun BillHistory(
    modifier: Modifier = Modifier,
    state: SubscriberDetailsUiState,
    onAction: (SubscriberDetailsAction) -> Unit = {}
) {
    var isExpanded by remember { mutableStateOf(false) }

    Column(
        modifier = modifier
            .fillMaxWidth()
            .padding(
                bottom = SimbanyuTheme.padding.large
            ),
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
                                onAction(SubscriberDetailsAction.FilterBill(year))
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
                    .fillMaxWidth(),
                verticalArrangement = Arrangement.spacedBy(
                    SimbanyuTheme.spacing.medium
                ),
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
                        iconId = R.drawable.ic_contract
                        iconTint = SimbanyuTheme.colors.statusNegativeText
                        iconBackgroundColor = SimbanyuTheme.colors.statusNegative
                    }

                    val openDialog = remember { mutableStateOf(false) }

                    when {
                        openDialog.value -> {
                            AlertDialog(
                                title = {
                                    SimbanyuText(
                                        text = "Konfirmasi Pembayaran",
                                        style = SimbanyuTheme.typography.titleSection
                                    )
                                },
                                text = {
                                    SimbanyuText(
                                        text = "Lakukan pembayaran sebesar ${bill.amount} secara tunai?",
                                    )
                                },
                                onDismissRequest = {
                                    openDialog.value = false
                                },
                                confirmButton = {
                                    SimbanyuButton(
                                        modifier = Modifier
                                            .background(
                                                brush = SimbanyuTheme.colors.brush.brandVertical,
                                                shape = SimbanyuTheme.shapes.extraLarge
                                            ),
                                        textColor = SimbanyuTheme.colors.brandText,
                                        text = "Lanjutkan",
                                        onClick = {
                                            openDialog.value = false
                                            onAction(SubscriberDetailsAction.PayByCash(bill))
                                        },
                                    )
                                },
                                dismissButton = {
                                    SimbanyuButton(
                                        modifier = Modifier
                                            .background(
                                                color = SimbanyuTheme.colors.statusNegative,
                                                shape = SimbanyuTheme.shapes.extraLarge
                                            ),
                                        textColor = SimbanyuTheme.colors.statusNegativeText,
                                        text = "Batal",
                                        onClick = {
                                            openDialog.value = false
                                        },
                                    )
                                }
                            )
                        }
                    }

                    BillHistoryItem(
                        period = "${bill.periodMonth} ${bill.periodYear}",
                        amount = bill.amount,
                        status = bill.status.status,
                        iconId = iconId,
                        iconTint = iconTint,
                        iconBackgroundColor = iconBackgroundColor,
                        statusBackgroundColor = statusBackgroundColor,
                        canPayByCash = bill.status == UiBillingStatus.UNPAID || bill.status == UiBillingStatus.PENDING,
                        onClickPayByCash = {
                            openDialog.value = true
                        }
                    )
                }
            }
        } else {
            EmptyState(
                modifier = Modifier
                    .fillMaxWidth(),
                message = "Pelanggan ini belum memiliki riwayat tagihan"
            )
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