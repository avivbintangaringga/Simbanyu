package com.avivba.simbanyu.ui.screens.report

import android.Manifest
import android.content.Context
import android.location.LocationManager
import android.widget.Toast
import androidx.compose.animation.animateContentSize
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
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
import com.avivba.simbanyu.ui.components.emptystate.EmptyState
import com.avivba.simbanyu.ui.components.imageview.ImageView
import com.avivba.simbanyu.ui.components.item.ReportItem
import com.avivba.simbanyu.ui.components.minimap.MiniMap
import com.avivba.simbanyu.ui.components.text.SimbanyuText
import com.avivba.simbanyu.ui.components.textfield.SimbanyuTextField
import com.avivba.simbanyu.ui.screens.camera.CameraViewModel
import com.avivba.simbanyu.ui.shared.Screen
import com.avivba.simbanyu.ui.shared.model.UiCoordinate
import com.avivba.simbanyu.ui.theme.SimbanyuTheme
import com.avivba.simbanyu.ui.utils.ObserveAsEvent
import com.avivba.simbanyu.ui.utils.conditional
import com.avivba.simbanyu.ui.utils.dashedBorder
import com.google.accompanist.permissions.ExperimentalPermissionsApi
import com.google.accompanist.permissions.rememberMultiplePermissionsState
import kotlinx.coroutines.launch
import org.koin.compose.viewmodel.koinActivityViewModel
import org.koin.compose.viewmodel.koinViewModel


@Composable
fun ReportScreen(
    modifier: Modifier = Modifier,
    viewModel: ReportViewModel = koinViewModel(),
    cameraViewModel: CameraViewModel = koinActivityViewModel(),
    onNavigate: (Screen) -> Unit = {},
) {
    val cameraToken = "SUBSCRIBER_REPORT"
    val state by viewModel.state.collectAsStateWithLifecycle()
    val context: Context = LocalContext.current

    ObserveAsEvent(viewModel.event) {
        when (it) {
            is ReportEvent.OpenCamera -> {
                onNavigate(Screen.Camera)
                cameraViewModel.setToken(cameraToken)
            }

            is ReportEvent.ReportSent -> {
                Toast.makeText(
                    context,
                    "Laporan berhasil dikirim",
                    Toast.LENGTH_SHORT
                ).show()
                viewModel.onAction(ReportAction.Load)
            }
        }
    }

    ObserveAsEvent(cameraViewModel.cameraResult) {
        if (it != null && it.token == cameraToken && it.image.exists()) {
            viewModel.onAction(ReportAction.SetImage(it.image))
            cameraViewModel.clearResult()
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
            .fillMaxSize(),
        verticalArrangement = Arrangement.spacedBy(SimbanyuTheme.spacing.medium),
    ) {
        SimbanyuText(
            modifier = Modifier.padding(horizontal = SimbanyuTheme.padding.large),
            text = "Laporkan Gangguan",
            style = SimbanyuTheme.typography.displayMedium
        )

        SimbanyuText(
            modifier = Modifier.padding(horizontal = SimbanyuTheme.padding.large),
            text = "Sampaikan keluhan Anda terkait layanan air kami",
            style = SimbanyuTheme.typography.bodySmall,
            color = SimbanyuTheme.colors.brand
        )

        SecondaryTabRow(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = SimbanyuTheme.padding.large),
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

@OptIn(ExperimentalPermissionsApi::class)
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
            .verticalScroll(scrollState)
            .padding(SimbanyuTheme.padding.large),
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

            val context = LocalContext.current
            val locationPermissionState = rememberMultiplePermissionsState(
                listOf(
                    Manifest.permission.ACCESS_FINE_LOCATION,
                    Manifest.permission.ACCESS_COARSE_LOCATION
                )
            )

            fun getMyLocation() {
                if (!locationPermissionState.allPermissionsGranted) {
                    locationPermissionState.launchMultiplePermissionRequest()
                }

                if (locationPermissionState.allPermissionsGranted) {
                    val locationManager =
                        context.getSystemService(Context.LOCATION_SERVICE) as LocationManager
                    try {
                        val location =
                            locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER)
                                ?: locationManager.getLastKnownLocation(LocationManager.NETWORK_PROVIDER)

                        location?.let {
                            onAction(
                                ReportAction.SetMyLocation(
                                    UiCoordinate(
                                        it.latitude,
                                        it.longitude
                                    )
                                )
                            )
                        } ?: run {
                            Toast.makeText(
                                context,
                                "Gagal mendapatkan lokasi. Pastikan GPS aktif.",
                                Toast.LENGTH_SHORT
                            ).show()
                        }
                    } catch (e: SecurityException) {
                        Toast.makeText(context, "Izin lokasi diperlukan", Toast.LENGTH_SHORT).show()
                    }
                } else {
                    Toast.makeText(
                        context,
                        "Izin lokasi dibutuhkan",
                        Toast.LENGTH_SHORT
                    ).show()
                }
            }

            Row(
                modifier = Modifier
                    .clip(SimbanyuTheme.shapes.large)
                    .clickable {
                        getMyLocation()
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
                    color = SimbanyuTheme.colors.brand,
                )
            }

            if (state.locationCoordinate != null) {
                MiniMap(
                    modifier = Modifier
                        .fillMaxWidth(),
                    location = state.locationCoordinate
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

            if (state.image == null) {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(150.dp)
                        .clip(SimbanyuTheme.shapes.large)
                        .clickable {
                            onAction(ReportAction.OpenCamera)
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

            } else {
                ImageView(
                    model = state.image,
                    modifier = Modifier
                        .fillMaxWidth(),
                    onClick = {
                        onAction(ReportAction.OpenCamera)
                    },
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
        modifier = modifier
            .fillMaxSize(),
        isRefreshing = state.isRefreshing,
        onRefresh = {
            onAction(ReportAction.Refresh)
        }
    ) {
        if (state.reports.isNotEmpty()) {
            LazyColumn(
                modifier = Modifier
                    .fillMaxSize(),
                contentPadding = PaddingValues(SimbanyuTheme.padding.large),
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
        } else {
            EmptyState(
                message = "Anda belum memiliki riwayat laporan"
            )
        }
    }
}

@Preview(showBackground = true, device = Devices.PIXEL_7, showSystemUi = false)
@Composable
private fun ReportPreview() {
    ReportContent(
        state = ReportUiState(
            selectedTabIndex = 0,
        )
    )
}