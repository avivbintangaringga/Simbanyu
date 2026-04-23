package com.avivba.simbanyu.ui.screens.operator.map

import com.avivba.simbanyu.ui.shared.base.BaseViewModel

class MapViewModel(

) : BaseViewModel<MapUiState, MapUiAction, MapUiEvent>(
    MapUiState()
) {
    override fun onAction(action: MapUiAction) {
        when (action) {
            is MapUiAction.SelectPoint -> {
                // TODO: Select Point
            }
        }
    }
}