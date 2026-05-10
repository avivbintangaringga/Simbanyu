package com.avivba.simbanyu.ui.screens.operator.meterrecord

import androidx.compose.foundation.text.input.TextFieldState
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.viewModelScope
import androidx.navigation.toRoute
import com.avivba.simbanyu.domain.base.Result
import com.avivba.simbanyu.domain.model.UsageSubmission
import com.avivba.simbanyu.domain.model.UsageUpdateSubmission
import com.avivba.simbanyu.domain.repository.RefreshEventBus
import com.avivba.simbanyu.domain.repository.SubscriberRepository
import com.avivba.simbanyu.domain.repository.UsageRepository
import com.avivba.simbanyu.ui.shared.Screen
import com.avivba.simbanyu.ui.shared.base.BaseViewModel
import com.avivba.simbanyu.ui.utils.toFullDateTime
import kotlinx.coroutines.async
import kotlinx.coroutines.awaitAll
import kotlinx.coroutines.launch

class MeterRecordViewModel(
    private val subscriberRepository: SubscriberRepository,
    private val usageRepository: UsageRepository,
    private val refreshEventBus: RefreshEventBus,
    savedStateHandle: SavedStateHandle
) : BaseViewModel<MeterRecordUiState, MeterRecordAction, MeterRecordEvents>(
    MeterRecordUiState()
) {
    private val subscriberId = savedStateHandle.toRoute<Screen.RecordMeter>().subscriberId
    private val editMode = savedStateHandle.toRoute<Screen.RecordMeter>().editMode

    init {
        viewModelScope.launch {
            updateState {
                it.copy(
                    isEditMode = editMode
                )
            }

            load()
        }

        viewModelScope.launch {
            refreshEventBus.refreshEvent.collect {
                load()
            }
        }
    }

    override fun onAction(action: MeterRecordAction) {
        viewModelScope.launch {
            when (action) {
                is MeterRecordAction.OpenCamera -> {
                    sendEvent(MeterRecordEvents.OpenCamera)
                }

                is MeterRecordAction.SendRecord -> {
                    send()
                }

                MeterRecordAction.NavigateBack -> {
                    sendEvent(MeterRecordEvents.NavigateBack)
                }

                is MeterRecordAction.SetImage -> {
                    updateState {
                        it.copy(
                            image = action.image
                        )
                    }
                }
            }
        }
    }

    private fun validateFields(): Boolean {
        var isValid = true

        if (state.value.meterRecordState.text.isEmpty()) {
            updateState {
                it.copy(
                    errorFields = it.errorFields + Fields.METER_RECORD
                )
            }

            isValid = false
        } else {
            if (state.value.meterRecordState.text.toString()
                    .toInt() <= state.value.lastMeterValue
            ) {
                updateState {
                    it.copy(
                        errorFields = it.errorFields + Fields.METER_RECORD
                    )
                }
            }
        }

        if (state.value.image == null && state.value.imageUrl.isNullOrEmpty()) {
            updateState {
                it.copy(
                    errorFields = it.errorFields + Fields.IMAGE
                )
            }
            isValid = false
        }

        return isValid
    }

    private suspend fun send() {
        updateState {
            it.copy(
                errorFields = emptyList()
            )
        }

        if (!validateFields()) return

        updateState {
            it.copy(
                isSendingRecord = true
            )
        }

        if (!editMode) {
            val submission = UsageSubmission(
                subscriberId = subscriberId,
                currentMeter = state.value.meterRecordState.text.toString().toInt(),
                evidenceImage = state.value.image!!,
                operatorNotes = state.value.notesState.text.toString(),
            )

            when (usageRepository.recordUsage(submission)) {
                is Result.Success -> {
                    sendEvent(MeterRecordEvents.RecordSent)
                    refreshEventBus.triggerRefresh()
                }

                is Result.Error -> {
                    // TODO: Handle error
                }
            }
        } else {
            val submission = UsageUpdateSubmission(
                currentMeter = state.value.meterRecordState.text.toString().toInt(),
                evidenceImage = state.value.image,
                operatorNotes = state.value.notesState.text.toString(),
            )

            when (usageRepository.updateUsage(state.value.usageId, submission)) {
                is Result.Success -> {
                    sendEvent(MeterRecordEvents.RecordSent)
                    refreshEventBus.triggerRefresh()
                }

                is Result.Error -> {
                    // TODO: Handle error
                }
            }
        }

        updateState {
            it.copy(
                isSendingRecord = false
            )
        }
    }

    private suspend fun load() {
        updateState {
            it.copy(
                isLoading = true
            )
        }

        listOf(
            viewModelScope.async {
                fetchSubscriberData()
            },
            viewModelScope.async {
                fetchUsageData()
            }
        ).awaitAll()

        updateState {
            it.copy(
                isLoading = false
            )
        }
    }

    private suspend fun fetchSubscriberData() {
        when (val subscriber = subscriberRepository.getById(subscriberId)) {
            is Result.Success -> {
                val data = subscriber.data

                updateState {
                    it.copy(
                        name = data.user.name,
                        address = data.address,
                        meterId = data.meterId,
                        plan = data.plan,
                    )
                }
            }

            is Result.Error -> {
                // TODO: Handle error
            }
        }
    }

    private suspend fun fetchUsageData() {
        when (val usage = usageRepository.getLastUsage(subscriberId, includeInitial = true)) {
            is Result.Success -> {
                val data = usage.data

                if (editMode) {
                    updateState {
                        it.copy(
                            usageId = data.id,
                            lastMeterRecordDate = data.date.toFullDateTime(),
                            lastMeterValue = data.meterStart,
                            meterRecordState = TextFieldState(data.meterEnd.toString()),
                            notesState = TextFieldState(data.operatorNotes ?: ""),
                            imageUrl = data.evidenceImageUrl
                        )
                    }
                } else {
                    updateState {
                        it.copy(
                            lastMeterRecordDate = data.date.toFullDateTime(),
                            lastMeterValue = data.meterEnd
                        )
                    }
                }

            }

            is Result.Error -> {
                // TODO: Handle error
            }
        }
    }
}