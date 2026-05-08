package com.avivba.simbanyu.ui.screens.camera

import java.io.File

data class CameraResult(
    val token: String,
    val image: File
)

data class CameraUiState(
    val isUsingFlash: Boolean = false,
    val isFrontCamera: Boolean = false,
    val currentToken: String? = null,
)

sealed interface CameraAction {
    object ToggleFlash : CameraAction
    object ToggleCamera : CameraAction
    data class ImageCaptured(val file: File) : CameraAction
}

sealed interface CameraEvent {
    data class ImageCaptured(val result: CameraResult) : CameraEvent
}