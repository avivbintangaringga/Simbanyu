package com.avivba.simbanyu.ui.screens.operator.announcement

import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.pulltorefresh.PullToRefreshBox
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Devices
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.avivba.simbanyu.R
import com.avivba.simbanyu.ui.components.buttons.SimbanyuButton
import com.avivba.simbanyu.ui.components.item.AnnouncementItem
import com.avivba.simbanyu.ui.components.text.SimbanyuText
import com.avivba.simbanyu.ui.components.topbar.TopBar
import com.avivba.simbanyu.ui.shared.Screen
import com.avivba.simbanyu.ui.theme.SimbanyuTheme
import com.avivba.simbanyu.ui.utils.ObserveAsEvent
import org.koin.compose.viewmodel.koinViewModel


@Composable
fun OperatorAnnouncementScreen(
    modifier: Modifier = Modifier,
    viewModel: OperatorAnnouncementViewModel = koinViewModel(),
    onNavigate: (Screen) -> Unit = {},
    onNavigateBack: () -> Unit = {}
) {
    val state by viewModel.state.collectAsStateWithLifecycle()

    ObserveAsEvent(viewModel.event) {
        when (it) {
            OperatorAnnouncementEvent.NavigateBack -> {
                onNavigateBack()
            }
        }
    }

    OperatorAnnouncementContent(
        modifier = modifier,
        state = state,
        onNavigate = onNavigate,
        onAction = viewModel::onAction
    )
}

@Composable
fun OperatorAnnouncementContent(
    modifier: Modifier = Modifier,
    state: OperatorAnnouncementUiState,
    onAction: (OperatorAnnouncementAction) -> Unit = {},
    onNavigate: (Screen) -> Unit = {}
) {
    val listState = rememberLazyListState()

    Scaffold(
        modifier = modifier,
        containerColor = Color.Transparent,
        topBar = {
            TopBar(
                text = "Simbanyu Operator",
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
                                onAction(OperatorAnnouncementAction.NavigateBack)
                            }
                            .padding(
                                SimbanyuTheme.padding.large
                            )
                    )
                }
            )
        },
    ) { paddingValues ->
        PullToRefreshBox(
            modifier = Modifier
                .padding(paddingValues),
            isRefreshing = state.isRefreshing,
            onRefresh = {
                onAction(OperatorAnnouncementAction.Refresh)
            }
        ) {
            Column(
                modifier = modifier
                    .fillMaxSize()
                    .padding(SimbanyuTheme.padding.large),
                verticalArrangement = Arrangement.spacedBy(SimbanyuTheme.spacing.medium),
            ) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Column(
                        modifier = Modifier
                            .weight(1f)
                    ) {
                        SimbanyuText(
                            modifier = Modifier,
                            text = "Pemberitahuan",
                            style = SimbanyuTheme.typography.displayMedium
                        )

                        SimbanyuText(
                            text = "Sampaikan pemberitahuan kepada pelanggan",
                            style = SimbanyuTheme.typography.bodySmall,
                            color = SimbanyuTheme.colors.brand
                        )
                    }
                    SimbanyuButton(
                        text = "Tambah",
                        textColor = SimbanyuTheme.colors.actionBrand,
                        modifier = Modifier.border(
                            1.dp,
                            SimbanyuTheme.colors.actionBrand,
                            shape = SimbanyuTheme.shapes.extraLarge
                        ),
                        iconLeft = {
                            Icon(
                                painter = painterResource(R.drawable.ic_add),
                                contentDescription = null,
                                tint = SimbanyuTheme.colors.actionBrand
                            )
                        }
                    ) {
                        onAction(OperatorAnnouncementAction.ShowCreateDialog)
                    }
                }
//
//                Spacer(Modifier.height(SimbanyuTheme.spacing.medium))


                LazyColumn(
                    modifier = Modifier
                        .fillMaxSize(),
                    state = listState,
                    verticalArrangement = Arrangement.spacedBy(
                        SimbanyuTheme.spacing.medium
                    )
                ) {
                    items(
                        items = state.announcements.sortedByDescending { it.id },
                        key = { it.id }
                    ) { announcement ->
                        AnnouncementItem(
                            title = announcement.title,
                            content = announcement.content,
                            time = announcement.time,
                            iconId = R.drawable.ic_campaign_filled,
                            canDelete = true,
                            onDelete = {
                                onAction(
                                    OperatorAnnouncementAction.DeleteAnnouncement(
                                        announcement
                                    )
                                )
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
private fun OperatorAnnouncementPreview() {
    OperatorAnnouncementContent(
        state = OperatorAnnouncementUiState(
            announcements = fakeAnnouncements
        )
    )
}