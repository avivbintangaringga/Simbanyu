package com.avivba.simbanyu.ui.screens.operator.map

import android.Manifest
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.activity.result.launch
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.avivba.simbanyu.R
import com.avivba.simbanyu.ui.components.text.SimbanyuText
import com.avivba.simbanyu.ui.shared.Screen
import com.avivba.simbanyu.ui.theme.SimbanyuTheme
import com.avivba.simbanyu.ui.utils.ObserveAsEvent
import com.google.accompanist.permissions.ExperimentalPermissionsApi
import com.google.accompanist.permissions.rememberMultiplePermissionsState
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.model.CameraPosition
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.LatLngBounds
import com.google.maps.android.compose.ComposeMapColorScheme
import com.google.maps.android.compose.GoogleMap
import com.google.maps.android.compose.MapProperties
import com.google.maps.android.compose.MapType
import com.google.maps.android.compose.Marker
import com.google.maps.android.compose.MarkerComposable
import com.google.maps.android.compose.MarkerInfoWindow
import com.google.maps.android.compose.MarkerInfoWindowComposable
import com.google.maps.android.compose.currentCameraPositionState
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
            is MapUiEvent.GoToSubscriberDetails -> {
                onNavigate(Screen.SubscriberDetails(it.subscriberId))
            }
        }
    }

    MapContent(
        modifier = modifier,
        state = state,
        onAction = viewModel::onAction
    )
}

@OptIn(ExperimentalPermissionsApi::class)
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

    val jatiayu = LatLng(-7.911178168148756, 110.69031351790268)
    val cameraPositionState = rememberCameraPositionState {
        position = CameraPosition.fromLatLngZoom(jatiayu, 15f)
    }

    LaunchedEffect(state.subscribers) {
        if (state.subscribers.isEmpty()) return@LaunchedEffect

        val builder = LatLngBounds.Builder()
        state.subscribers.forEach { subscriber ->
            builder.include(LatLng(subscriber.coordinate.lat, subscriber.coordinate.lng))
        }
        val bounds = builder.build()
        val center = bounds.center

        cameraPositionState.animate(
            update = CameraUpdateFactory.newCameraPosition(
                CameraPosition.fromLatLngZoom(center, 15f)
            )
        )

        cameraPositionState.animate(
            update = CameraUpdateFactory.newLatLngBounds(bounds, 100),
            durationMs = 1000
        )
    }

    val mapsProperties = remember(state.hasLocationPermission) {
        MapProperties(
            isMyLocationEnabled = locationPermissionState.allPermissionsGranted,
            mapType = MapType.HYBRID
        )
    }

    val context = LocalContext.current

    GoogleMap(
        modifier = Modifier.fillMaxSize(),
        cameraPositionState = cameraPositionState,
        properties = mapsProperties
    ) {
        state.subscribers.forEach { subscriber ->
            MarkerInfoWindowComposable(
                keys = arrayOf(subscriber.id),
                state = rememberUpdatedMarkerState(
                    position = LatLng(
                        subscriber.coordinate.lat,
                        subscriber.coordinate.lng
                    )
                ),
                onClick = { marker ->
//                    onAction(MapUiAction.SelectPoint(subscriber.coordinate))
//                    marker.showInfoWindow()
//                    marker.showInfoWindow()
                    false
                },
                infoContent = {
                    Box(
                        modifier = Modifier
                            .height(32.dp)
                            .width(100.dp)
                            .background(Color.Red)
                    ) { }
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
}

@Preview(showBackground = true)
@Composable
private fun MapContentPreview() {
    MapContent(
        state = fakeMapUiState
    )
}