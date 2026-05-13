package com.avivba.simbanyu.ui.screens.operator.dashboard

import androidx.activity.compose.ReportDrawnWhen
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.pulltorefresh.PullToRefreshBox
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.avivba.simbanyu.R
import com.avivba.simbanyu.ui.components.buttons.SimbanyuCardButton
import com.avivba.simbanyu.ui.components.emptystate.EmptyState
import com.avivba.simbanyu.ui.components.item.ActivityItem
import com.avivba.simbanyu.ui.components.loadingbox.LoadingBox
import com.avivba.simbanyu.ui.components.text.SimbanyuText
import com.avivba.simbanyu.ui.shared.Screen
import com.avivba.simbanyu.ui.theme.SimbanyuTheme
import com.avivba.simbanyu.ui.utils.ObserveAsEvent
import org.koin.compose.viewmodel.koinViewModel

@Composable
fun OperatorDashboardScreen(
    modifier: Modifier = Modifier,
    viewModel: OperatorDashboardViewModel = koinViewModel(),
    onNavigate: (Screen) -> Unit = {},
    onChangeTab: (Screen) -> Unit = {}
) {
    val state by viewModel.state.collectAsStateWithLifecycle()

    ObserveAsEvent(viewModel.event) {
        when (it) {
            OperatorDashboardEvent.GoToAnnouncementsPage -> {
                onNavigate(Screen.OperatorAnnouncementScreen)
            }
        }
    }

    ReportDrawnWhen {
        !state.isLoading
    }

    LoadingBox(
        modifier = modifier,
        isLoading = state.isLoading
    ) {
        PullToRefreshBox(
            isRefreshing = state.isRefreshing,
            onRefresh = {
                viewModel.onAction(OperatorDashboardAction.Refresh)
            }
        ) {
            OperatorDashboardContent(
                modifier = Modifier,
                state = state,
                onAction = viewModel::onAction,
                onChangeTab = onChangeTab
            )
        }
    }
}

@Composable
fun OperatorDashboardContent(
    modifier: Modifier = Modifier,
    state: OperatorDashboardUiState,
    onAction: (OperatorDashboardAction) -> Unit = {},
    onChangeTab: (Screen) -> Unit = {}
) {
    val scrollState = rememberScrollState()

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

                SimbanyuText(
                    modifier = Modifier
                        .padding(
                            top = SimbanyuTheme.padding.small
                        )
                        .background(
                            color = SimbanyuTheme.colors.brand,
                            shape = SimbanyuTheme.shapes.large
                        )
                        .padding(
                            vertical = SimbanyuTheme.padding.small,
                            horizontal = SimbanyuTheme.padding.large
                        ),
                    text = "OPERATOR",
                    style = SimbanyuTheme.typography.bodySmall,
                    color = SimbanyuTheme.colors.brandText
                )


            }
        }


        SimbanyuText(
            modifier = Modifier.padding(
                SimbanyuTheme.padding.small
            ),
            text = "Akses Cepat",
            style = SimbanyuTheme.typography.titleSection,
        )

        FlowRow(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(
                SimbanyuTheme.spacing.medium
            ),
            verticalArrangement = Arrangement.spacedBy(
                SimbanyuTheme.spacing.medium
            ),
            maxItemsInEachRow = 2
        ) {
            SimbanyuCardButton(
                modifier = Modifier.weight(1f),
                iconId = R.drawable.ic_group_filled,
                title = "Pelanggan",
                subTitle = "Catat meter pelanggan",
                onClick = {
                    onChangeTab(Screen.OperatorMainScreen.Subscribers)
                }
            )

            SimbanyuCardButton(
                modifier = Modifier.weight(1f),
                iconId = R.drawable.ic_campaign_filled,
                iconTint = SimbanyuTheme.colors.statusPositiveText,
                title = "Pemberitahuan",
                subTitle = "Sampaikan informasi",
                onClick = {
                    onAction(OperatorDashboardAction.ViewAllAnnouncements)
                }
            )

            SimbanyuCardButton(
                modifier = Modifier.weight(1f),
                iconId = R.drawable.ic_map_filled,
                iconTint = SimbanyuTheme.colors.statusWarningText,
                title = "Peta",
                subTitle = "Lihat sebaran pelanggan",
                onClick = {
                    onChangeTab(Screen.OperatorMainScreen.Map)
                }
            )

            SimbanyuCardButton(
                modifier = Modifier.weight(1f),
                iconId = R.drawable.ic_feedback_filled,
                iconTint = SimbanyuTheme.colors.statusNegativeText,
                title = "Laporan",
                subTitle = "Lihat aduan pelanggan",
                onClick = {
                    onChangeTab(Screen.OperatorMainScreen.Report)
                }
            )
        }

        SimbanyuText(
            modifier = Modifier.padding(
                SimbanyuTheme.padding.small
            ),
            text = "Aktivitas Terbaru",
            style = SimbanyuTheme.typography.titleSection,
        )

        if (state.activities.isNotEmpty()) {
            Column(
                modifier = Modifier
                    .fillMaxWidth(),
                verticalArrangement = Arrangement.spacedBy(
                    SimbanyuTheme.spacing.medium
                )
            ) {
                state.activities.forEach { activity ->
                    ActivityItem(
                        activity = activity
                    )
                }
            }
        } else {
            EmptyState(
                message = "Belum ada aktivitas"
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun OperatorDashboardPreview() {
    OperatorDashboardContent(
        state = fakeOperatorDashboardUiState
    )
}