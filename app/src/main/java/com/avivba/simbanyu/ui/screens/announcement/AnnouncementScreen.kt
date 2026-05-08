package com.avivba.simbanyu.ui.screens.announcement

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
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
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Devices
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.avivba.simbanyu.R
import com.avivba.simbanyu.ui.components.item.AnnouncementItem
import com.avivba.simbanyu.ui.components.text.SimbanyuText
import com.avivba.simbanyu.ui.components.topbar.TopBar
import com.avivba.simbanyu.ui.shared.Screen
import com.avivba.simbanyu.ui.theme.SimbanyuTheme
import com.avivba.simbanyu.ui.utils.ObserveAsEvent
import org.koin.compose.viewmodel.koinViewModel


@Composable
fun AnnouncementScreen(
    modifier: Modifier = Modifier,
    viewModel: AnnouncementViewModel = koinViewModel(),
    onNavigate: (Screen) -> Unit = {},
    onNavigateBack: () -> Unit = {}
) {
    val state by viewModel.state.collectAsStateWithLifecycle()

    ObserveAsEvent(viewModel.event) {
        when (it) {
            AnnouncementEvent.NavigateBack -> {
                onNavigateBack()
            }
        }
    }

    AnnouncementContent(
        modifier = modifier,
        state = state,
        onNavigate = onNavigate,
        onAction = viewModel::onAction
    )
}

@Composable
fun AnnouncementContent(
    modifier: Modifier = Modifier,
    state: AnnouncementUiState,
    onAction: (AnnouncementAction) -> Unit = {},
    onNavigate: (Screen) -> Unit = {}
) {
    val listState = rememberLazyListState()

    Scaffold(
        modifier = modifier,
        containerColor = Color.Transparent,
        topBar = {
            TopBar(
                text = "Simbanyu",
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
                                onAction(AnnouncementAction.NavigateBack)
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
                onAction(AnnouncementAction.Refresh)
            }
        ) {
            Column(
                modifier = modifier
                    .fillMaxSize()
                    .padding(SimbanyuTheme.padding.large),
                verticalArrangement = Arrangement.spacedBy(SimbanyuTheme.spacing.medium),
            ) {
                    SimbanyuText(
                        modifier = Modifier,
                        text = "Pemberitahuan",
                        style = SimbanyuTheme.typography.displayMedium
                    )

                SimbanyuText(
                    text = "Informasi terkini mengenai layanan air",
                    style = SimbanyuTheme.typography.bodySmall,
                    color = SimbanyuTheme.colors.brand
                )

                Spacer(Modifier.height(SimbanyuTheme.spacing.medium))

                LazyColumn(
                    modifier = Modifier
                        .fillMaxSize(),
                    state = listState,
                    verticalArrangement = Arrangement.spacedBy(
                        SimbanyuTheme.spacing.medium
                    )
                ) {
                    items(
                        items = state.announcements,
                        key = { it.id }
                    ) { announcement ->
                        AnnouncementItem(
                            title = announcement.title,
                            content = announcement.body,
                            time = announcement.date,
                            iconId = R.drawable.ic_campaign_filled,
                        )
                    }
                }
            }
        }
    }
}

@Preview(showBackground = true, device = Devices.PIXEL_7, showSystemUi = false)
@Composable
private fun AnnouncementPreview() {
    AnnouncementContent(
        state = AnnouncementUiState(
            announcements = fakeAnnouncements
        )
    )
}