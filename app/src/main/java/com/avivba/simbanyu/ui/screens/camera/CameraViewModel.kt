package com.avivba.simbanyu.ui.screens.camera

import androidx.lifecycle.viewModelScope
import com.avivba.simbanyu.ui.screens.camera.CameraEvent.ImageCaptured
import com.avivba.simbanyu.ui.shared.base.BaseViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class CameraViewModel : BaseViewModel<CameraUiState, CameraAction, CameraEvent>(
    CameraUiState()
) {
    private val _cameraResult = MutableStateFlow<CameraResult?>(null)
    val cameraResult = _cameraResult.asStateFlow()

    override fun onAction(action: CameraAction) {
        viewModelScope.launch {
            when (action) {
                CameraAction.ToggleFlash -> {
                    updateState {
                        it.copy(
                            isUsingFlash = !state.value.isUsingFlash
                        )
                    }
                }

                CameraAction.ToggleCamera -> {
                    updateState {
                        it.copy(
                            isFrontCamera = !state.value.isFrontCamera
                        )
                    }
                }

                is CameraAction.ImageCaptured -> {
                    if (!state.value.currentToken.isNullOrEmpty()) {
                        val result = CameraResult(
                            token = state.value.currentToken!!,
                            image = action.file
                        )

                        delay(100)

                        _cameraResult.update { result }

                        sendEvent(
                            ImageCaptured(result)
                        )
                    }
                }

                is CameraAction.SetCaptureState -> {
                    updateState {
                        it.copy(
                            isCapturing = action.isCapturing
                        )
                    }
                }
            }
        }
    }

    fun setToken(token: String) {
        updateState {
            it.copy(
                currentToken = token
            )
        }
    }

    fun clearResult() {
        updateState {
            it.copy(
                currentToken = null
            )
        }

        _cameraResult.update { null }
    }
}