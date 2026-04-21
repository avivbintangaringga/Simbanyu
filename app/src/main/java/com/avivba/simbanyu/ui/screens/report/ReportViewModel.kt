package com.avivba.simbanyu.ui.screens.report

import androidx.compose.foundation.text.input.TextFieldState
import androidx.lifecycle.viewModelScope
import com.avivba.simbanyu.ui.core.BaseViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class ReportViewModel : BaseViewModel<ReportUiState, ReportAction, ReportEvent>(
    ReportUiState()
) {
    init {
        load()
    }

    override fun onAction(action: ReportAction) {
        when (action) {
            is ReportAction.PickImage -> {
                // TODO: Implement pick image
                updateState {
                    it.copy(
                        imageUri = "https://example.com/image.jpg"
                    )
                }
            }

            is ReportAction.GetMyLocation -> {
            }

            is ReportAction.SendReport -> {
                sendReport()
            }

            is ReportAction.ChangeTab -> {
                changeTab(action.index)
            }

            is ReportAction.Load -> {
                load()
            }

            is ReportAction.Refresh -> {
                refresh()
            }

            is ReportAction.ExpandItem -> {
                setItemExpanded(action.report)
            }
        }
    }

    private fun validateFields(): Boolean  {
        var isValid = true

        if (state.value.locationState.text.isEmpty()) {
            updateState {
                it.copy(
                    errorFields = it.errorFields + Fields.LOCATION
                )
            }

            isValid = false
        }

        if (state.value.descriptionState.text.isEmpty()) {
            updateState {
                it.copy(
                    errorFields = it.errorFields + Fields.DESCRIPTION
                )
            }

            isValid = false
        }

        if (state.value.imageUri == null) {
            updateState {
                it.copy(
                    errorFields = it.errorFields + Fields.IMAGE
                )
            }

            isValid = false
        }

        return isValid
    }

    private fun sendReport() {
        if (!state.value.isSendingReport) {
            updateState {
                it.copy(
                    errorFields = emptyList()
                )
            }

            if (!validateFields()) {
                return
            }

            viewModelScope.launch {
                updateState {
                    it.copy(
                        isSendingReport = true
                    )
                }

                delay(2000)

                // TODO: Implement send report

                updateState {
                    it.copy(
                        locationState = TextFieldState(),
                        descriptionState = TextFieldState(),
                        locationCoordinate = null,
                        imageUri = null,
                        isSendingReport = false
                    )
                }
                sendEvent(ReportEvent.ReportSent)
            }
        }
    }

    private fun changeTab(index: Int) {
        updateState {
            it.copy(
                selectedTabIndex = index
            )
        }
    }

    private fun refresh() {
        // TODO: Implement refresh
    }

    private fun setItemExpanded(report: Report) {
        updateState {
            it.copy(
                reports = it.reports.map { item ->
                    if (item.id == report.id) {
                        item.copy(
                            isExpanded = !item.isExpanded
                        )
                    } else {
                        item
                    }
                }
            )
        }
    }

    private fun load() {
        viewModelScope.launch {
            updateState {
                it.copy(
                    reports = fakeReports
                )
            }
        }
    }
}