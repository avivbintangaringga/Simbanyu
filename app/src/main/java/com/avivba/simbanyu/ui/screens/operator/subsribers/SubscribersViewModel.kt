package com.avivba.simbanyu.ui.screens.operator.subsribers

import android.util.Log
import androidx.compose.runtime.snapshotFlow
import androidx.lifecycle.viewModelScope
import com.avivba.simbanyu.domain.base.Result
import com.avivba.simbanyu.domain.repository.SubscriberRepository
import com.avivba.simbanyu.ui.shared.base.BaseViewModel
import com.avivba.simbanyu.ui.shared.model.Subscriber
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class SubscribersViewModel(
    private val subscriberRepository: SubscriberRepository
) : BaseViewModel<SubscribersUiState, SubscribersAction, SubscribersEvent>(
    SubscribersUiState()
) {
    private val _subscribers = MutableStateFlow<List<Subscriber>>(emptyList())

    init {
        viewModelScope.launch {
            load()
            observeDataChanges()
        }
    }

    override fun onAction(action: SubscribersAction) {
        viewModelScope.launch {
            when (action) {
                SubscribersAction.Load -> load()
                SubscribersAction.Refresh -> refresh()
                is SubscribersAction.ShowDetails -> {
                    sendEvent(SubscribersEvent.GoToSubscriberDetails(action.subscriber))
                }

                is SubscribersAction.SetRecordStateFilter -> {
                    updateState {
                        it.copy(
                            filterRecordState = action.filter
                        )
                    }
                }

                is SubscribersAction.RecordMeter -> {
                    sendEvent(SubscribersEvent.GoToRecordMeter(action.subscriber))
                }
            }
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

    private suspend fun fetchData() {
        Log.d("SDKSDK", "fetchData")
        when (val subscribers = subscriberRepository.getAll()) {
            is Result.Success -> {
                Log.d("SDKSDK", "fetchData: Success")
                _subscribers.update {
                    subscribers.data.map {
                        Subscriber.fromDomain(it)
                    }
                }
            }

            is Result.Error -> {
                Log.d("SDKSDK", "fetchData: Error")
                // TODO: Handle error
            }
        }
    }

    private suspend fun observeDataChanges() {
        combine(
            _subscribers,
            state.map { it.filterRecordState }.distinctUntilChanged(),
            snapshotFlow {
                state.value.searchBarState.text.toString()
            }.distinctUntilChanged()
        ) { subscribers, status, query ->
            Log.d("SDKSDK", "observeDataChanges: $subscribers")

            val filteredByState = subscribers.filter {
                if (status == FilterRecordState.ALL) {
                    return@filter true
                } else {
                    return@filter it.isRecorded == (status == FilterRecordState.RECORDED)
                }
            }
            if (query.isEmpty()) {
                return@combine filteredByState
            } else {
                filteredByState.filter {
                    it.name.contains(query, ignoreCase = true) ||
                            it.meterId.contains(query, ignoreCase = true) ||
                            it.phone.contains(query, ignoreCase = true)
                }
            }
        }.collect { filtered ->
            updateState {
                it.copy(
                    filteredSubscribers = filtered
                )
            }
        }
    }
}
