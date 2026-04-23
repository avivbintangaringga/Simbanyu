package com.avivba.simbanyu.ui.screens.operator.map

import com.avivba.simbanyu.ui.screens.operator.subsribers.fakeSubscribers
import com.avivba.simbanyu.ui.shared.model.Coordinate
import com.avivba.simbanyu.ui.shared.model.Subscriber

data class MapUiState(
    val isLoading: Boolean = false,
    val subscribers: List<Subscriber> = emptyList(),
    val hasLocationPermission: Boolean = false
)

sealed interface MapUiAction {
    data class SelectPoint(val coordinate: Coordinate) : MapUiAction
    data class SetLocationPermission(val isGranted: Boolean) : MapUiAction
}

sealed interface MapUiEvent {
    data class GoToSubscriberDetails(val subscriberId: String) : MapUiEvent
}

val fakeMapUiState = MapUiState(
    subscribers = fakeSubscribers
)