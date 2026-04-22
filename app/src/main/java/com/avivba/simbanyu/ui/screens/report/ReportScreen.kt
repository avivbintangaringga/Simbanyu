package com.avivba.simbanyu.ui.screens.report

import android.content.Context
import android.widget.Toast
import androidx.compose.animation.animateContentSize
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.requiredSize
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.input.TextFieldLineLimits
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.SecondaryTabRow
import androidx.compose.material3.Tab
import androidx.compose.material3.pulltorefresh.PullToRefreshBox
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Devices
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.util.fastAny
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.avivba.simbanyu.R
import com.avivba.simbanyu.ui.components.buttons.SimbanyuButton
import com.avivba.simbanyu.ui.components.item.ReportItem
import com.avivba.simbanyu.ui.components.text.SimbanyuText
import com.avivba.simbanyu.ui.components.textfield.SimbanyuTextField
import com.avivba.simbanyu.ui.shared.Screen
import com.avivba.simbanyu.ui.theme.SimbanyuTheme
import com.avivba.simbanyu.ui.utils.ObserveAsEvent
import com.avivba.simbanyu.ui.utils.conditional
import com.avivba.simbanyu.ui.utils.dashedBorder
import kotlinx.coroutines.launch
import org.koin.compose.viewmodel.koinViewModel

@Composable
fun ReportScreen(
    modifier: Modifier = Modifier,
    viewModel: ReportViewModel = koinViewModel(),
    onNavigate: (Screen) -> Unit = {},
) {
    val state by viewModel.state.collectAsStateWithLifecycle()
    val context: Context = LocalContext.current

    ObserveAsEvent(viewModel.event) {
        when (it) {
            is ReportEvent.ImagePicked -> {}
            is ReportEvent.ReportSent -> {
                Toast.makeText(
                    context,
                    "Laporan berhasil dikirim",
                    Toast.LENGTH_SHORT
                ).show()
            }
        }
    }

    ReportContent(
        modifier = modifier,
        state = state,
        onAction = viewModel::onAction
    )
}

@Composable
fun ReportContent(
    modifier: Modifier = Modifier,
    state: ReportUiState,
    onAction: (ReportAction) -> Unit = {}
) {
    val tabs = listOf("Buat laporan", "Riwayat")
    val pagerState = rememberPagerState {
        tabs.size
    }
    val scope = rememberCoroutineScope()

    LaunchedEffect(pagerState.currentPage) {
        onAction(ReportAction.ChangeTab(pagerState.currentPage))
    }

    LaunchedEffect(state.selectedTabIndex) {
        if (state.selectedTabIndex != pagerState.currentPage) {
            pagerState.animateScrollToPage(state.selectedTabIndex)
        }
    }
    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(SimbanyuTheme.padding.large),
        verticalArrangement = Arrangement.spacedBy(SimbanyuTheme.spacing.medium),
    ) {
        SimbanyuText(
            text = "Laporkan Gangguan",
            style = SimbanyuTheme.typography.displayMedium
        )

        SimbanyuText(
            text = "Sampaikan keluhan Anda terkait layanan air kami",
            style = SimbanyuTheme.typography.bodySmall,
            color = SimbanyuTheme.colors.brand
        )

        SecondaryTabRow(
            modifier = Modifier
                .fillMaxWidth(),
            selectedTabIndex = pagerState.currentPage,
            containerColor = SimbanyuTheme.colors.brandText,
            indicator = {
                Box(
                    modifier = Modifier
                        .tabIndicatorOffset(state.selectedTabIndex)
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
                    selected = state.selectedTabIndex == index,
                    onClick = {
                        scope.launch {
                            pagerState.animateScrollToPage(index)
                        }

                        onAction(ReportAction.ChangeTab(index))
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
            modifier = Modifier
                .weight(1f)
        ) { page ->
            when (page) {
                0 -> {
                    CreateReport(
                        modifier = Modifier
                            .fillMaxWidth(),
                        state = state,
                        onAction = onAction
                    )
                }

                1 -> {
                    ReportList(
                        modifier = Modifier
                            .fillMaxWidth(),
                        state = state,
                        onAction = onAction
                    )
                }
            }
        }

    }
}

@Composable
private fun CreateReport(
    modifier: Modifier,
    state: ReportUiState,
    onAction: (ReportAction) -> Unit = {},
) {
    val scrollState = rememberScrollState()

    Column(
        modifier = modifier
            .fillMaxSize()
            .verticalScroll(scrollState),
        verticalArrangement = Arrangement.spacedBy(SimbanyuTheme.spacing.medium),
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .background(
                    color = SimbanyuTheme.colors.surfaceCardAlternate,
                    shape = SimbanyuTheme.shapes.large
                )
                .padding(SimbanyuTheme.padding.large),
            verticalArrangement = Arrangement.spacedBy(SimbanyuTheme.spacing.medium)
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
            ) {
                Icon(
                    modifier = Modifier
                        .height(16.dp)
                        .padding(end = SimbanyuTheme.padding.medium),
                    painter = painterResource(R.drawable.ic_lock_filled),
                    contentDescription = null,
                    tint = SimbanyuTheme.colors.brand
                )
                SimbanyuText(
                    text = "LOKASI GANGGUAN",
                    style = SimbanyuTheme.typography.labelOverline
                )
            }

            SimbanyuTextField(
                modifier = Modifier
                    .conditional(state.errorFields.fastAny { it == Fields.LOCATION }) {
                        border(
                            width = 2.dp,
                            color = SimbanyuTheme.colors.statusNegativeText,
                            shape = SimbanyuTheme.shapes.large

                        )
                    },
                state = state.locationState,
                lineLimits = TextFieldLineLimits.MultiLine(3, 6),
                hint = "Masukkan lokasi / patokan terjadinya gangguan...",
            )

            Row(
                modifier = Modifier
                    .clip(SimbanyuTheme.shapes.large)
                    .clickable {
                        onAction(ReportAction.GetMyLocation)
                    }
                    .padding(SimbanyuTheme.padding.small),
                horizontalArrangement = Arrangement.spacedBy(
                    SimbanyuTheme.spacing.medium
                ),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Icon(
                    modifier = Modifier.height(16.dp),
                    painter = painterResource(R.drawable.ic_my_location_filled),
                    contentDescription = null,
                    tint = SimbanyuTheme.colors.brand
                )

                SimbanyuText(
                    text = "Gunakan lokasi saya",
                    style = SimbanyuTheme.typography.bodyProminent,
                    color = SimbanyuTheme.colors.brand
                )
            }
        }

        Column(
            modifier = Modifier
                .fillMaxWidth()
                .background(
                    color = SimbanyuTheme.colors.surfaceCardAlternate,
                    shape = SimbanyuTheme.shapes.large
                )
                .padding(SimbanyuTheme.padding.large),
            verticalArrangement = Arrangement.spacedBy(SimbanyuTheme.spacing.medium)
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
            ) {
                Icon(
                    modifier = Modifier
                        .height(16.dp)
                        .padding(end = SimbanyuTheme.padding.medium),
                    painter = painterResource(R.drawable.ic_description_filled),
                    contentDescription = null,
                    tint = SimbanyuTheme.colors.brand
                )
                SimbanyuText(
                    text = "DESKRIPSI",
                    style = SimbanyuTheme.typography.labelOverline
                )
            }

            SimbanyuTextField(
                modifier = Modifier
                    .conditional(state.errorFields.fastAny { it == Fields.DESCRIPTION }) {
                        border(
                            width = 2.dp,
                            color = SimbanyuTheme.colors.statusNegativeText,
                            shape = SimbanyuTheme.shapes.large

                        )
                    },
                state = state.descriptionState,
                lineLimits = TextFieldLineLimits.MultiLine(6, 8),
                hint = "Ceritakan detail gangguan yang dialami...",
            )
        }

        Column(
            modifier = Modifier
                .fillMaxWidth()
                .background(
                    color = SimbanyuTheme.colors.surfaceCardAlternate,
                    shape = SimbanyuTheme.shapes.large
                )
                .padding(SimbanyuTheme.padding.large),
            verticalArrangement = Arrangement.spacedBy(SimbanyuTheme.spacing.medium)
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
            ) {
                Icon(
                    modifier = Modifier
                        .height(16.dp)
                        .padding(end = SimbanyuTheme.padding.medium),
                    painter = painterResource(R.drawable.ic_photo_camera_filled),
                    contentDescription = null,
                    tint = SimbanyuTheme.colors.brand
                )
                SimbanyuText(
                    text = "BUKTI FOTO",
                    style = SimbanyuTheme.typography.labelOverline
                )
            }

            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(150.dp)
                    .clip(SimbanyuTheme.shapes.large)
                    .clickable {
                        onAction(ReportAction.PickImage)
                    }
                    .conditional(
                        state.errorFields.fastAny {
                            it == Fields.IMAGE
                        }
                    ) {
                        dashedBorder(
                            strokeWidth = 4.dp,
                            gapLength = 6.dp,
                            dashLength = 8.dp,
                            color = SimbanyuTheme.colors.statusNegativeText,
                            shape = SimbanyuTheme.shapes.large
                        )
                    }
                    .dashedBorder(
                        strokeWidth = 4.dp,
                        gapLength = 6.dp,
                        dashLength = 8.dp,
                        color = SimbanyuTheme.colors.borderCardAlternate,
                        shape = SimbanyuTheme.shapes.large
                    ),
                verticalArrangement = Arrangement.spacedBy(
                    SimbanyuTheme.spacing.medium,
                    alignment = Alignment.CenterVertically
                ),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Icon(
                    modifier = Modifier
                        .requiredSize(40.dp),
                    painter = painterResource(R.drawable.ic_photo_camera_filled),
                    contentDescription = null,
                    tint = SimbanyuTheme.colors.statusNeutral
                )

                SimbanyuText(
                    text = "Unggah foto (maks 5MB)",
                    style = SimbanyuTheme.typography.bodySmall,
                    color = SimbanyuTheme.colors.statusNeutral
                )
            }

        }

        SimbanyuButton(
            modifier = Modifier
                .fillMaxWidth()
                .background(
                    brush = SimbanyuTheme.colors.brush.brandVertical
                ),
            text = "Kirim Laporan",
            iconRight = {
                if (state.isSendingReport) {
                    CircularProgressIndicator(
                        modifier = Modifier.size(18.dp),
                        strokeWidth = 2.dp,
                        color = SimbanyuTheme.colors.actionBrandText
                    )
                } else {
                    Icon(
                        painter = painterResource(R.drawable.ic_send),
                        contentDescription = null,
                        tint = SimbanyuTheme.colors.brandText
                    )
                }
            },
            onClick = {
                onAction(ReportAction.SendReport)
            }
        )
    }
}

@Composable
private fun ReportList(
    modifier: Modifier = Modifier,
    state: ReportUiState,
    onAction: (ReportAction) -> Unit
) {
    val listState = rememberLazyListState()
    PullToRefreshBox(
        isRefreshing = state.isRefreshing,
        onRefresh = {
            onAction(ReportAction.Refresh)
        }
    ) {
        LazyColumn(
            modifier = modifier
                .fillMaxSize(),
            verticalArrangement = Arrangement.spacedBy(
                SimbanyuTheme.spacing.medium
            ),
            state = listState
        ) {
            items(
                items = state.reports,
                key = { it.id }
            ) { report ->
                Column(
                    modifier = Modifier
                        .animateContentSize()
                ) {
                    ReportItem(
                        modifier = Modifier,
                        report = report,
                        onClickExpand = {
                            onAction(ReportAction.ToggleExpandItem(report))
                        }
                    )
                }
            }
        }
    }
}

@Preview(showBackground = true, device = Devices.PIXEL_7, showSystemUi = false)
@Composable
private fun ReportPreview() {
    ReportContent(
        state = ReportUiState(
            selectedTabIndex = 0,
            reports = fakeReports
        )
    )
}