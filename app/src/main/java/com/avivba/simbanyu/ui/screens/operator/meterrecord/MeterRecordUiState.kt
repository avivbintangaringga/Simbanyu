package com.avivba.simbanyu.ui.screens.operator.meterrecord

import androidx.compose.foundation.text.input.TextFieldState
import java.io.File

enum class Fields {
    METER_RECORD,
    IMAGE,
}

data class MeterRecordUiState(
    val isLoading: Boolean = true,
    val isSendingRecord: Boolean = false,
    val isEditMode: Boolean = false,
    val usageId: String = "",
    val name: String = "",
    val address: String = "",
    val meterId: String = "",
    val plan: String = "",
    val lastMeterRecordDate: String = "",
    val lastMeterValue: Int = 0,
    val meterRecordState: TextFieldState = TextFieldState(),
    val notesState: TextFieldState = TextFieldState(),
    val imageUrl: String? = null,
    val image: File? = null,
    val errorFields: List<Fields> = emptyList()
)

sealed interface MeterRecordAction {
    data object OpenCamera : MeterRecordAction
    data class SetImage(val image: File) : MeterRecordAction
    data object SendRecord : MeterRecordAction
    data object NavigateBack : MeterRecordAction
}

sealed interface MeterRecordEvents {
    data object NavigateBack : MeterRecordEvents
    data object OpenCamera : MeterRecordEvents
    data object RecordSent : MeterRecordEvents
}

val fakeMeterRecordState = MeterRecordUiState(
    name = "Budi Santoso",
    address = "Jl. Merdeka No. 10, Jakarta",
    meterId = "MTR001",
    plan = "Rumah Tangga A",
    lastMeterRecordDate = "10 Januari 2026 13:33",
    lastMeterValue = 1032,
)
