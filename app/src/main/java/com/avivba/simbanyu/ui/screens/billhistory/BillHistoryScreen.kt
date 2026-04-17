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
import androidx.compose.material3.Text
import androidx.compose.material3.pulltorefresh.PullToRefreshBox
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Devices
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.avivba.simbanyu.R
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
    val scrollState = rememberScrollState()
    var isExpanded by remember { mutableStateOf(false) }

    Column(
        modifier = modifier
            .fillMaxSize()
            .verticalScroll(scrollState)
            .padding(SimbanyuTheme.padding.large),
        verticalArrangement = Arrangement.spacedBy(SimbanyuTheme.spacing.medium),
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
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
                        text = if (state.selectedYear > 0) state.selectedYear.toString() else "Pilih tahun",
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
    }
}

@Preview(showBackground = true, device = Devices.PIXEL_7, showSystemUi = false)
@Composable
fun BillHistoryPreview() {
    BillHistoryContent(
        state = BillHistoryUiState()
    )
}