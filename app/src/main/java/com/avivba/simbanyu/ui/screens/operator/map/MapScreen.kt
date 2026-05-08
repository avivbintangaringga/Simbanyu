package com.avivba.simbanyu.ui.screens.operator.map

import android.Manifest
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutVertically
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.avivba.simbanyu.R
import com.avivba.simbanyu.ui.components.buttons.SimbanyuButton
import com.avivba.simbanyu.ui.components.text.SimbanyuText
import com.avivba.simbanyu.ui.screens.operator.subsribers.fakeSubscribers
import com.avivba.simbanyu.ui.shared.Screen
import com.avivba.simbanyu.ui.theme.SimbanyuTheme
import com.avivba.simbanyu.ui.utils.ObserveAsEvent
import com.google.accompanist.permissions.ExperimentalPermissionsApi
import com.google.accompanist.permissions.rememberMultiplePermissionsState
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.model.CameraPosition
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.LatLngBounds
import com.google.maps.android.compose.GoogleMap
import com.google.maps.android.compose.MapProperties
import com.google.maps.android.compose.MapType
import com.google.maps.android.compose.MarkerComposable
import com.google.maps.android.compose.rememberCameraPositionState
import com.google.maps.android.compose.rememberUpdatedMarkerState
import kotlinx.coroutines.launch
import org.koin.compose.viewmodel.koinViewModel

@Composable
fun MapScreen(
    modifier: Modifier = Modifier,
    viewModel: MapViewModel = koinViewModel(),
    onNavigate: (Screen) -> Unit = {}
) {
    val state by viewModel.state.collectAsStateWithLifecycle()

    ObserveAsEvent(viewModel.event) {
        when (it) {
            is MapUiEvent.GoToRecordMeter -> {
                onNavigate(Screen.RecordMeter(it.subscriberId))
            }
        }
    }

    MapContent(
        modifier = modifier,
        state = state,
        onAction = viewModel::onAction
    )
}

@OptIn(ExperimentalPermissionsApi::class, ExperimentalMaterial3Api::class)
@Composable
private fun MapContent(
    modifier: Modifier = Modifier,
    state: MapUiState,
    onAction: (MapUiAction) -> Unit = {}
) {
    val locationPermissionState = rememberMultiplePermissionsState(
        listOf(
            Manifest.permission.ACCESS_FINE_LOCATION,
            Manifest.permission.ACCESS_COARSE_LOCATION
        )
    )

    LaunchedEffect(locationPermissionState.allPermissionsGranted) {
        onAction(MapUiAction.SetLocationPermission(locationPermissionState.allPermissionsGranted))
    }

    LaunchedEffect(Unit) {
        if (!locationPermissionState.allPermissionsGranted) {
            locationPermissionState.launchMultiplePermissionRequest()
        }
    }

    val jatiayu = LatLng(-7.911178168148756, 110.69031351790268)
    val cameraPositionState = rememberCameraPositionState {
        position = CameraPosition.fromLatLngZoom(jatiayu, 15f)
    }

    var isMapLoaded by remember { mutableStateOf(false) }

    LaunchedEffect(state.subscribers, isMapLoaded) {
        if (isMapLoaded && state.subscribers.isNotEmpty() && !state.hasInitialZoomed) {
            val bounds = LatLngBounds.Builder().apply {
                state.subscribers.forEach {
                    include(
                        LatLng(
                            it.coordinate.latitude,
                            it.coordinate.longitude
                        )
                    )
                }
            }.build()

            val cameraUpdate = CameraUpdateFactory.newLatLngBounds(
                bounds,
                100
            )

            cameraPositionState.animate(
                update = cameraUpdate,
                durationMs = 1000
            )

            onAction(MapUiAction.HasInitialZoomed)
        }
    }

    val mapsProperties by remember(state.hasLocationPermission) {
        mutableStateOf(
            MapProperties(
                isMyLocationEnabled = state.hasLocationPermission,
                mapType = MapType.HYBRID
            )
        )
    }

    val scope = rememberCoroutineScope()

    val density = LocalDensity.current
    var bottomPaddingPx by remember { mutableIntStateOf(0) }
    val animatedBottomPadding by androidx.compose.animation.core.animateIntAsState(
        targetValue = bottomPaddingPx,
        label = "MapBottomPadding"
    )

    val mapPadding = remember(animatedBottomPadding) {
        PaddingValues(
            bottom = with(density) {
                animatedBottomPadding.toDp()
            }
        )
    }

    Box(
        modifier = modifier.fillMaxSize(),
        contentAlignment = Alignment.BottomCenter
    ) {
        // TODO: Add refresh button?
        GoogleMap(
            modifier = Modifier.matchParentSize(),
            cameraPositionState = cameraPositionState,
            properties = mapsProperties,
            onMapClick = {
                onAction(MapUiAction.UnselectSubscriber)
            },
            onMapLoaded = {
                isMapLoaded = true
            },
            contentPadding = mapPadding
        ) {
            state.subscribers.forEach { subscriber ->
                MarkerComposable(
                    state = rememberUpdatedMarkerState(
                        position = LatLng(
                            subscriber.coordinate.latitude,
                            subscriber.coordinate.longitude
                        )
                    ),
                    title = subscriber.name,
                    onClick = {
                        onAction(MapUiAction.SelectSubscriber(subscriber))
                        scope.launch {
                            cameraPositionState.animate(
                                update = CameraUpdateFactory.newLatLng(
                                    LatLng(
                                        subscriber.coordinate.latitude,
                                        subscriber.coordinate.longitude
                                    )
                                ),
                                durationMs = 500
                            )
                        }
                        true
                    },
                    content = {
                        val tint = if (subscriber.isRecorded) {
                            SimbanyuTheme.colors.brand
                        } else {
                            SimbanyuTheme.colors.statusWarningText
                        }
                        Icon(
                            modifier = Modifier
                                .size(24.dp)
                                .background(
                                    color = SimbanyuTheme.colors.brandText,
                                    shape = CircleShape
                                )
                                .padding(
                                    SimbanyuTheme.padding.small
                                ),
                            painter = painterResource(R.drawable.ic_person_filled),
                            contentDescription = null,
                            tint = tint
                        )
                    }
                )
            }
        }

        LaunchedEffect(state.selectedSubscriber) {
            if (state.selectedSubscriber == null) {
                bottomPaddingPx = 0
            }
        }

        AnimatedVisibility(
            visible = state.selectedSubscriber != null,
            enter = slideInVertically(
                initialOffsetY = { it }
            ),
            exit = slideOutVertically(
                targetOffsetY = { it }
            )
        ) {
            var lastSubscriber by remember { mutableStateOf(fakeSubscribers[0]) }
            val subscriber = remember(state.selectedSubscriber) {
                if (state.selectedSubscriber != null) {
                    lastSubscriber = state.selectedSubscriber
                }
                lastSubscriber
            }

            Column(
                modifier = modifier
                    .onGloballyPositioned { coordinates ->
                        if (state.selectedSubscriber != null) {
                            bottomPaddingPx = coordinates.size.height
                        }
                    }
                    .clip(
                        SimbanyuTheme.shapes.bottomBar
                    )
                    .background(
                        SimbanyuTheme.colors.surfaceCardAlternate
                    )
                    .fillMaxWidth()
                    .padding(
                        SimbanyuTheme.padding.large
                    ),
            ) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(
                        SimbanyuTheme.spacing.medium
                    ),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Icon(
                        modifier = Modifier
                            .size(40.dp)
                            .background(
                                color = SimbanyuTheme.colors.brandText,
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
                            SimbanyuTheme.spacing.small
                        )
                    ) {
                        Row(
                            modifier = Modifier
                                .fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceBetween,
                            verticalAlignment = Alignment.CenterVertically
                        ) {

                            SimbanyuText(
                                text = subscriber.name,
                                style = SimbanyuTheme.typography.displaySmall
                            )

                            var status = "BELUM DICATAT"
                            var iconId = R.drawable.ic_warning_filled
                            var color = SimbanyuTheme.colors.statusWarningText

                            if (subscriber.isRecorded) {
                                status = "SELESAI"
                                iconId = R.drawable.ic_check
                                color = SimbanyuTheme.colors.statusPositiveText
                            }

                            Row(
                                modifier = Modifier
                                    .background(
                                        color = color,
                                        shape = SimbanyuTheme.shapes.large
                                    )
                                    .padding(
                                        vertical = SimbanyuTheme.padding.extraSmall,
                                        horizontal = SimbanyuTheme.padding.medium
                                    ),
                                verticalAlignment = Alignment.CenterVertically,
                                horizontalArrangement = Arrangement.spacedBy(
                                    SimbanyuTheme.spacing.small
                                )
                            ) {
                                Icon(
                                    modifier = Modifier
                                        .size(12.dp),
                                    painter = painterResource(iconId),
                                    contentDescription = null,
                                    tint = SimbanyuTheme.colors.brandText,
                                )

                                SimbanyuText(
                                    text = status,
                                    style = SimbanyuTheme.typography.bodySmall,
                                    color = SimbanyuTheme.colors.brandText,
                                )
                            }
                        }

                        SimbanyuText(
                            text = subscriber.address,
                            style = SimbanyuTheme.typography.bodySmall
                        )
                    }
                }

                Spacer(Modifier.height(SimbanyuTheme.spacing.medium))

                Row(
                    modifier = Modifier,
                    horizontalArrangement = Arrangement.spacedBy(
                        SimbanyuTheme.spacing.small
                    ),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Icon(
                        modifier = Modifier
                            .height(16.dp),
                        painter = painterResource(R.drawable.ic_speed_filled),
                        contentDescription = null,
                        tint = SimbanyuTheme.colors.brand
                    )

                    SimbanyuText(
                        text = subscriber.meterId,
                        style = SimbanyuTheme.typography.labelOverline
                    )
                }
                Row(
                    modifier = Modifier
                        .fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Column(
                        modifier = Modifier
                            .weight(1f),
                        verticalArrangement = Arrangement.spacedBy(
                            SimbanyuTheme.spacing.extraSmall
                        )
                    ) {
                        Row(
                            modifier = Modifier
                                .fillMaxWidth(),
                            horizontalArrangement = Arrangement.spacedBy(
                                SimbanyuTheme.spacing.small
                            ),
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Icon(
                                modifier = Modifier
                                    .height(16.dp),
                                painter = painterResource(R.drawable.ic_calendar_clock_filled),
                                contentDescription = null,
                                tint = SimbanyuTheme.colors.brand
                            )

                            SimbanyuText(
                                text = "CATAT TERAKHIR",
                                style = SimbanyuTheme.typography.bodySmall.copy(
                                    fontWeight = FontWeight(900)
                                )
                            )
                        }
                        SimbanyuText(
                            text = subscriber.lastMeterRecordDate,
                            style = SimbanyuTheme.typography.bodySmall
                        )
                    }

                    if (subscriber.isRecorded) {
                        SimbanyuButton(
                            text = "Perbarui",
                            modifier = Modifier
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
                                onAction(MapUiAction.GoToRecordMeter(subscriber))
                            }
                        )
                    } else {
                        SimbanyuButton(
                            text = "Catat",
                            modifier = Modifier
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
                                onAction(MapUiAction.GoToRecordMeter(subscriber))
                            }
                        )
                    }
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun MapContentPreview() {
    MapContent(
        state = fakeMapUiState.copy(
            selectedSubscriber = fakeSubscribers[0]
        )
    )
}