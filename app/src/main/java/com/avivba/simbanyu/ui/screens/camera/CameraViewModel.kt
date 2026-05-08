package com.avivba.simbanyu.ui.screens.camera

import androidx.lifecycle.viewModelScope
import com.avivba.simbanyu.ui.shared.base.BaseViewModel
import kotlinx.coroutines.launch

class CameraViewModel : BaseViewModel<CameraUiState, CameraAction, CameraEvent>(
    CameraUiState()
) {
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
                    sendEvent(CameraEvent.ImageCaptured(action.file))
                }
            }
        }
    }
}