package com.avivba.simbanyu.ui.screens.report

import androidx.compose.foundation.text.input.TextFieldState
import androidx.lifecycle.viewModelScope
import com.avivba.simbanyu.domain.base.Result
import com.avivba.simbanyu.domain.model.ReportSubmission
import com.avivba.simbanyu.domain.repository.ReportRepository
import com.avivba.simbanyu.ui.shared.base.BaseViewModel
import com.avivba.simbanyu.ui.shared.model.Report
import kotlinx.coroutines.launch

class ReportViewModel(
    private val reportRepository: ReportRepository
) : BaseViewModel<ReportUiState, ReportAction, ReportEvent>(
    ReportUiState()
) {
    init {
        viewModelScope.launch {
            load()
        }
    }

    override fun onAction(action: ReportAction) {
        viewModelScope.launch {
            when (action) {
                is ReportAction.OpenCamera -> {
                    sendEvent(ReportEvent.OpenCamera)
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

                is ReportAction.ToggleExpandItem -> {
                    toggleReportExpand(action.report)
                }

                is ReportAction.SetImage -> {
                    updateState {
                        it.copy(
                            image = action.file
                        )
                    }
                }
            }
        }
    }

    private fun validateFields(): Boolean {
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

        if (state.value.image == null || !state.value.image!!.isFile) {
            updateState {
                it.copy(
                    errorFields = it.errorFields + Fields.IMAGE
                )
            }

            isValid = false
        }

        return isValid
    }

    private suspend fun sendReport() {
        if (!state.value.isSendingReport) {
            updateState {
                it.copy(
                    errorFields = emptyList()
                )
            }

            if (!validateFields()) {
                return
            }

            updateState {
                it.copy(
                    isSendingReport = true
                )
            }

            val submitResult = reportRepository.submit(
                ReportSubmission(
                    address = state.value.locationState.text.toString(),
                    coordinate = null, // TODO: Get my location
                    description = state.value.descriptionState.text.toString(),
                    evidenceImage = state.value.image!!
                )
            )

            when (submitResult) {
                is Result.Success -> {
                    sendEvent(ReportEvent.ReportSent)
                }

                is Result.Error -> {
                    // TODO: Handle error
                }
            }

            updateState {
                it.copy(
                    locationState = TextFieldState(),
                    descriptionState = TextFieldState(),
                    locationCoordinate = null,
                    image = null,
                    isSendingReport = false
                )
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

    private fun toggleReportExpand(report: Report) {
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

    private suspend fun refresh() {
        updateState {
            it.copy(
                isRefreshing = true
            )
        }

        fetchData()

        updateState {
            it.copy(
                isRefreshing = false
            )
        }
    }

    private suspend fun load() {
        updateState {
            it.copy(
                isLoading = true
            )
        }

        fetchData()

        updateState {
            it.copy(
                isLoading = false
            )
        }
    }

    private suspend fun fetchData() {
        when (val reports = reportRepository.getAll()) {
            is Result.Success -> {
                updateState {
                    it.copy(
                        reports = reports.data.map {
                            Report.fromDomain(it)
                        }
                    )
                }
            }

            is Result.Error -> {
                // TODO: Handle error
            }
        }
    }
}