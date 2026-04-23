package com.avivba.simbanyu.ui.screens.operator.map

import androidx.lifecycle.viewModelScope
import com.avivba.simbanyu.ui.screens.operator.subsribers.fakeSubscribers
import com.avivba.simbanyu.ui.shared.base.BaseViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class MapViewModel(

) : BaseViewModel<MapUiState, MapUiAction, MapUiEvent>(
    MapUiState()
) {
    init {
        viewModelScope.launch {
            delay(3000)
            load()
        }
    }
    override fun onAction(action: MapUiAction) {
        when (action) {
            is MapUiAction.SelectPoint -> {
                // TODO: Select Point
            }

            is MapUiAction.SetLocationPermission -> {
                updateState {
                    it.copy(
                        hasLocationPermission = action.isGranted
                    )
                }
            }
        }
    }

    private fun load() {
        updateState {
            it.copy(
                isLoading = true
            )
        }

        // TODO: Load Subscriber

        updateState {
            it.copy(
                isLoading = false,
                subscribers = fakeSubscribers
            )
        }
    }
}