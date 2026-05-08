package com.avivba.simbanyu.ui.screens.camera

data class CameraUiState(
    val isUsingFlash: Boolean = false,
    val isFrontCamera: Boolean = false,
)

sealed interface CameraAction {
    object ToggleFlash : CameraAction
    object ToggleCamera : CameraAction
}

sealed interface CameraEvent {}