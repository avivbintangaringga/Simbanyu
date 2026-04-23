package com.avivba.simbanyu.ui.screens.operator.meterrecord

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.viewModelScope
import androidx.navigation.toRoute
import com.avivba.simbanyu.ui.shared.Screen
import com.avivba.simbanyu.ui.shared.base.BaseViewModel
import kotlinx.coroutines.launch

class MeterRecordViewModel (
    savedStateHandle: SavedStateHandle
) : BaseViewModel<MeterRecordUiState, MeterRecordAction, MeterRecordEvents>(
    MeterRecordUiState()
) {
    private val subscriberId = savedStateHandle.toRoute<Screen.RecordMeter>().id

    override fun onAction(action: MeterRecordAction) {
        viewModelScope.launch {
            when (action) {
                is MeterRecordAction.PickImage -> {
                    sendEvent(MeterRecordEvents.PickImage)
                }
                is MeterRecordAction.SendRecord -> {
                    // TOOD: Implement send record
                    sendEvent(MeterRecordEvents.RecordSent)
                }

                MeterRecordAction.NavigateBack -> {
                    sendEvent(MeterRecordEvents.NavigateBack)
                }
            }
        }
    }
}