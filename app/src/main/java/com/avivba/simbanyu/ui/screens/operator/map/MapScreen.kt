package com.avivba.simbanyu.ui.screens.operator.map

import android.Manifest
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.activity.result.launch
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.avivba.simbanyu.ui.shared.Screen
import com.avivba.simbanyu.ui.utils.ObserveAsEvent
import com.google.android.gms.maps.model.CameraPosition
import com.google.android.gms.maps.model.LatLng
import com.google.maps.android.compose.GoogleMap
import com.google.maps.android.compose.MapProperties
import com.google.maps.android.compose.Marker
import com.google.maps.android.compose.rememberCameraPositionState
import com.google.maps.android.compose.rememberUpdatedMarkerState
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

@Composable
private fun MapContent(
    modifier: Modifier = Modifier,
    state: MapUiState,
    onAction: (MapUiAction) -> Unit = {}
) {
    val locationPermissionLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.RequestMultiplePermissions()
    ) { permissions ->
        val fineLocationGranted =
            permissions.getOrDefault(Manifest.permission.ACCESS_FINE_LOCATION, false)
        val coarseLocationGranted =
            permissions.getOrDefault(Manifest.permission.ACCESS_COARSE_LOCATION, false)

        onAction(MapUiAction.SetLocationPermission(fineLocationGranted || coarseLocationGranted))
    }

    LaunchedEffect(Unit) {
        locationPermissionLauncher.launch(
            arrayOf(
                Manifest.permission.ACCESS_FINE_LOCATION,
                Manifest.permission.ACCESS_COARSE_LOCATION
            )
        )
    }

    val singapore = LatLng(1.35, 103.87)
    val singaporeMarkerState = rememberUpdatedMarkerState(position = singapore)
    val cameraPositionState = rememberCameraPositionState {
        position = CameraPosition.fromLatLngZoom(singapore, 10f)
    }

    val mapsProperties = remember(state.hasLocationPermission) {
        MapProperties(
            isMyLocationEnabled = state.hasLocationPermission
        )
    }

    GoogleMap(
        modifier = Modifier.fillMaxSize(),
        cameraPositionState = cameraPositionState,
        properties = mapsProperties
    ) {
        Marker(
            state = singaporeMarkerState,
            title = "Singapore",
            snippet = "Marker in Singapore"
        )
    }
}

@Preview(showBackground = true)
@Composable
private fun MapContentPreview() {
    MapContent(
        state = fakeMapUiState
    )
}