package com.avivba.simbanyu.ui.screens.report

import androidx.lifecycle.viewModelScope
import com.avivba.simbanyu.ui.base.BaseViewModel
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
            }
            is ReportAction.GetMyLocation -> {
            }
            is ReportAction.SendReport -> {
            }
            is ReportAction.ChangeTab -> {
                updateState {
                    it.copy(
                        selectedTabIndex = action.index
                    )
                }
            }

            ReportAction.Load -> {
                load()
            }
            ReportAction.Refresh -> {}
            is ReportAction.ExpandItem -> {
                setItemExpanded(action.report)
            }
        }
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