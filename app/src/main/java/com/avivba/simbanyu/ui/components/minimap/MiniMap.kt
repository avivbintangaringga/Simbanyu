package com.avivba.simbanyu.ui.components.minimap

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.avivba.simbanyu.R
import com.avivba.simbanyu.ui.shared.model.Coordinate
import com.avivba.simbanyu.ui.theme.SimbanyuTheme
import com.google.android.gms.maps.model.CameraPosition
import com.google.android.gms.maps.model.LatLng
import com.google.maps.android.compose.GoogleMap
import com.google.maps.android.compose.MapProperties
import com.google.maps.android.compose.MapType
import com.google.maps.android.compose.MapUiSettings
import com.google.maps.android.compose.MarkerComposable
import com.google.maps.android.compose.rememberCameraPositionState
import com.google.maps.android.compose.rememberUpdatedMarkerState

val DefaultMiniMapMarker = @Composable {
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
        tint = SimbanyuTheme.colors.brand
    )
}

@Composable
fun MiniMap(
    modifier: Modifier = Modifier,
    location: Coordinate,
    marker: @Composable () -> Unit = DefaultMiniMapMarker,
    zoom: Float = 17f
) {
    val loc = LatLng(
        location.lat,
        location.lng
    )

    val cameraPositionState = rememberCameraPositionState {
        position = CameraPosition.fromLatLngZoom(loc, zoom)
    }

    val uiSettings = remember {
        MapUiSettings(
            zoomControlsEnabled = false
        )
    }

    val properties = remember {
        MapProperties(
            mapType = MapType.HYBRID
        )
    }

    GoogleMap(
        modifier = modifier
            .fillMaxWidth()
            .height(
                120.dp
            )
            .clip(
                SimbanyuTheme.shapes.large
            )
            .background(
                color = SimbanyuTheme.colors.statusNeutralText
            ),
        cameraPositionState = cameraPositionState,
        uiSettings = uiSettings,
        properties = properties
    ) {
        MarkerComposable(
            state = rememberUpdatedMarkerState(
                position = loc
            ),
            content = marker
        )
    }
}

@Preview
@Composable
private fun MiniMapPreview() {
    MiniMap(
        location = Coordinate(
            -7.911178168148756, 110.69031351790268
        )
    )
}
