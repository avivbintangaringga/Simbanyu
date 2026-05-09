package com.avivba.simbanyu.ui.screens.operator.subsribers

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material3.Icon
import androidx.compose.material3.pulltorefresh.PullToRefreshBox
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.avivba.simbanyu.R
import com.avivba.simbanyu.ui.components.emptystate.EmptyState
import com.avivba.simbanyu.ui.components.item.SubscriberItem
import com.avivba.simbanyu.ui.components.text.SimbanyuText
import com.avivba.simbanyu.ui.components.textfield.SimbanyuTextField
import com.avivba.simbanyu.ui.shared.Screen
import com.avivba.simbanyu.ui.theme.SimbanyuTheme
import com.avivba.simbanyu.ui.utils.ObserveAsEvent
import com.avivba.simbanyu.ui.utils.conditional
import org.koin.compose.viewmodel.koinViewModel

@Composable
fun SubscribersScreen(
    modifier: Modifier = Modifier,
    viewModel: SubscribersViewModel = koinViewModel(),
    onNavigate: (Screen) -> Unit = {},
) {
    val state by viewModel.state.collectAsStateWithLifecycle()

    ObserveAsEvent(viewModel.event) {
        when (it) {
            is SubscribersEvent.GoToSubscriberDetails -> {
                onNavigate(Screen.SubscriberDetails(it.subscriber.id))
            }

            is SubscribersEvent.GoToRecordMeter -> {
                onNavigate(Screen.RecordMeter(it.subscriber.id, it.subscriber.isRecorded))
            }
        }
    }

    PullToRefreshBox(
        modifier = modifier
            .fillMaxSize(),
        isRefreshing = state.isRefreshing,
        onRefresh = {
            viewModel.onAction(SubscribersAction.Refresh)
        }
    ) {
        SubscribersContent(
            state = state,
            onAction = viewModel::onAction,
            onNavigate = onNavigate
        )
    }
}

@Composable
fun SubscribersContent(
    modifier: Modifier = Modifier,
    state: SubscribersUiState,
    onAction: (SubscribersAction) -> Unit = {},
    onNavigate: (Screen) -> Unit = {}
) {
    val listState = rememberLazyListState()

    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(SimbanyuTheme.padding.large),
        verticalArrangement = Arrangement.spacedBy(SimbanyuTheme.spacing.medium),
    ) {
        SimbanyuText(
            text = "Data Pelanggan",
            style = SimbanyuTheme.typography.displayMedium
        )

        SimbanyuText(
            text = "Daftar pelanggan aktif",
            style = SimbanyuTheme.typography.bodySmall,
            color = SimbanyuTheme.colors.brand
        )

        SimbanyuTextField(
            modifier = Modifier,
            state = state.searchBarState,
            hint = "Nama Pelanggan / ID Meter / No. HP",
            prefix = {
                Icon(
                    painter = painterResource(R.drawable.ic_search),
                    contentDescription = "Username",
                    tint = SimbanyuTheme.colors.textInputDisabled
                )
            },
            imeAction = ImeAction.Search,
        )

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(
                SimbanyuTheme.spacing.medium
            )
        ) {
            FilterRecordState.entries.forEach {
                val selected = state.filterRecordState == it
                SimbanyuText(
                    modifier = Modifier
                        .clip(
                            SimbanyuTheme.shapes.large
                        )
                        .clickable {
                            onAction(SubscribersAction.SetRecordStateFilter(it))
                        }
                        .background(
                            color = SimbanyuTheme.colors.surfaceCard,
                        )
                        .conditional(selected) {
                            background(
                                color = SimbanyuTheme.colors.brand
                            )
                        }
                        .conditional(!selected) {
                            border(
                                width = 1.dp,
                                color = SimbanyuTheme.colors.brand,
                                shape = SimbanyuTheme.shapes.large
                            )
                        }
                        .padding(
                            vertical = SimbanyuTheme.padding.small,
                            horizontal = SimbanyuTheme.padding.large
                        ),
                    text = it.title,
                    style = SimbanyuTheme.typography.labelUnit,
                    color = if (selected) {
                        SimbanyuTheme.colors.brandText
                    } else {
                        SimbanyuTheme.colors.brand
                    }
                )
            }
        }

        if (state.filteredSubscribers.isNotEmpty()) {
            LazyColumn(
                modifier = modifier
                    .fillMaxSize(),
                verticalArrangement = Arrangement.spacedBy(
                    SimbanyuTheme.spacing.medium
                ),
                state = listState
            ) {
                items(
                    items = state.filteredSubscribers,
                    key = { it.id }
                ) { subscriber ->
                    SubscriberItem(
                        subscriber = subscriber,
                        onClick = {
                            onAction(SubscribersAction.ShowDetails(subscriber))
                        },
                        onClickRecordMeter = {
                            onAction(SubscribersAction.RecordMeter(subscriber))
                        }
                    )
                }
            }
        } else {
            EmptyState(
                modifier = Modifier.fillMaxSize(),
                message = "Tidak ada data pelanggan"
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun SubscribersContentPreview() {
    SubscribersContent(
        state = SubscribersUiState(
            filteredSubscribers = fakeSubscribers
        )
    )
}