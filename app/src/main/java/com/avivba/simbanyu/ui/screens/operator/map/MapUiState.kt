package com.avivba.simbanyu.ui.screens.operator.map

import com.avivba.simbanyu.ui.screens.operator.subsribers.fakeSubscribers
import com.avivba.simbanyu.ui.shared.model.Subscriber

data class MapUiState(
    val isLoading: Boolean = false,
    val subscribers: List<Subscriber> = emptyList(),
    val hasLocationPermission: Boolean = false,
    val selectedSubscriber: Subscriber? = null,
    val hasInitialZoomed: Boolean = false,
)

sealed interface MapUiAction {
    data class SelectSubscriber(val subscriber: Subscriber) : MapUiAction
    data object UnselectSubscriber : MapUiAction

    data class SetLocationPermission(val isGranted: Boolean) : MapUiAction
    data class GoToRecordMeter(val subscriber: Subscriber) : MapUiAction
    data object HasInitialZoomed : MapUiAction
}

sealed interface MapUiEvent {
    data class GoToRecordMeter(val subscriberId: String) : MapUiEvent
}

val fakeMapUiState = MapUiState(
    subscribers = fakeSubscribers
)