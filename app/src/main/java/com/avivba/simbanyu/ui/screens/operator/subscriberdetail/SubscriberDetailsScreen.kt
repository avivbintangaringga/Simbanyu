package com.avivba.simbanyu.ui.screens.operator.subscriberdetail

import android.widget.Toast
import androidx.compose.animation.core.animateDpAsState
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SecondaryTabRow
import androidx.compose.material3.Tab
import androidx.compose.material3.pulltorefresh.PullToRefreshBox
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateMapOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.platform.LocalUriHandler
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.avivba.simbanyu.R
import com.avivba.simbanyu.ui.components.buttons.SimbanyuButton
import com.avivba.simbanyu.ui.components.minimap.MiniMap
import com.avivba.simbanyu.ui.components.text.SimbanyuText
import com.avivba.simbanyu.ui.components.topbar.TopBar
import com.avivba.simbanyu.ui.shared.Screen
import com.avivba.simbanyu.ui.shared.Screen.RecordMeter
import com.avivba.simbanyu.ui.shared.model.UiCoordinate
import com.avivba.simbanyu.ui.theme.SimbanyuTheme
import com.avivba.simbanyu.ui.utils.ObserveAsEvent
import kotlinx.coroutines.launch
import org.koin.compose.viewmodel.koinViewModel


// TODO: Implement cash payment here

@Composable
fun SubscriberDetailsScreen(
    modifier: Modifier = Modifier,
    viewModel: SubscriberDetailsViewModel = koinViewModel(),
    onNavigate: (Screen) -> Unit = {},
    onNavigateBack: () -> Unit = {}
) {
    val state by viewModel.state.collectAsStateWithLifecycle()
    val context = LocalContext.current
    val uriHandler = LocalUriHandler.current

    ObserveAsEvent(viewModel.event) {
        when (it) {
            SubscriberDetailsEvent.NavigateBack -> {
                onNavigateBack()
            }

            is SubscriberDetailsEvent.GoToRecordMeter -> {
                onNavigate(RecordMeter(viewModel.subscriberId, it.editMode))
            }

            is SubscriberDetailsEvent.GoToWhatsApp -> {
                if (state.phone.isNotEmpty()) {
                    val cleanedPhone = if (state.phone.startsWith("0")) {
                        state.phone.substring(1)
                    } else {
                        state.phone
                    }

                    val url = "https://wa.me/62$cleanedPhone"
                    uriHandler.openUri(url)
                }
            }

            is SubscriberDetailsEvent.OnCashPaymentResult -> {
                Toast.makeText(
                    context,
                    it.message,
                    Toast.LENGTH_SHORT
                ).show()
            }
        }
    }

    PullToRefreshBox(
        modifier = modifier
            .fillMaxSize(),
        isRefreshing = state.isRefreshing,
        onRefresh = {
            viewModel.onAction(SubscriberDetailsAction.Refresh)
        }
    ) {
        SubscriberDetailsContent(
            modifier = Modifier,
            state = state,
            onAction = viewModel::onAction,
            onNavigate = onNavigate
        )
    }
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
    val pagerHeights = remember { mutableStateMapOf<Int, Int>() }
    val animatedHeight by animateDpAsState(
        targetValue = with(LocalDensity.current) {
            (pagerHeights[pagerState.currentPage] ?: 0).toDp().coerceAtLeast(1.dp)
        },
        label = "pagerHeight"
    )

    LaunchedEffect(pagerState.currentPage) {
        onAction(SubscriberDetailsAction.ChangeTab(pagerState.currentPage))
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

            if (state.location != null) {
                MiniMap(
                    location = state.location
                )
            }

            Row(
                modifier = Modifier,
                horizontalArrangement = Arrangement.spacedBy(
                    SimbanyuTheme.spacing.medium
                )
            ) {
                SimbanyuButton(
                    text = "WhatsApp",
                    modifier = Modifier
                        .weight(1f)
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
                        onAction(SubscriberDetailsAction.ContactViaWhatsApp)
                    }
                )

                if (state.isRecorded) {
                    SimbanyuButton(
                        text = "Perbarui",
                        modifier = Modifier
                            .weight(1f)
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
                                painter = painterResource(R.drawable.ic_speed_filled),
                                contentDescription = null,
                                tint = SimbanyuTheme.colors.actionBrandText
                            )
                        },
                        onClick = {
                            onAction(SubscriberDetailsAction.UpdateMeter)
                        }
                    )
                } else {
                    SimbanyuButton(
                        text = "Catat",
                        modifier = Modifier
                            .weight(1f)
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
            }

            SecondaryTabRow(
                modifier = Modifier
                    .fillMaxWidth(),
                selectedTabIndex = pagerState.currentPage,
                containerColor = SimbanyuTheme.colors.brandText,
                indicator = {
                    Box(
                        modifier = Modifier
                            .tabIndicatorOffset(pagerState.currentPage)
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
                        selected = pagerState.currentPage == index,
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
                beyondViewportPageCount = 1,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(animatedHeight)
            ) { page ->
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .wrapContentHeight(unbounded = true)
                        .onGloballyPositioned { layoutCoordinates ->
                            val height = layoutCoordinates.size.height
                            if (height > 0) {
                                pagerHeights[page] = height
                            }
                        }
                ) {
                    when (page) {
                        0 -> {
                            BillHistory(
                                state = state,
                                onAction = onAction
                            )
                        }

                        1 -> {
                            UsageHistory(
                                state = state,
                                onAction = onAction
                            )
                        }
                    }
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun SubscriberDetailsContentPreview() {
    SubscriberDetailsContent(
        state = fakeSubscriberDetailsUiState.copy(
            selectedTabIndex = 1,
            isRecorded = true,
            location = UiCoordinate(
                7.911178168148756,
                110.69031351790268
            )
        )
    )
}