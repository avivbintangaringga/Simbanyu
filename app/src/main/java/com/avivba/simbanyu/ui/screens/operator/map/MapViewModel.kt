package com.avivba.simbanyu.ui.screens.operator.map

import androidx.lifecycle.viewModelScope
import com.avivba.simbanyu.domain.base.Result
import com.avivba.simbanyu.domain.repository.RefreshEventBus
import com.avivba.simbanyu.domain.repository.SubscriberRepository
import com.avivba.simbanyu.ui.screens.operator.map.MapUiEvent.GoToRecordMeter
import com.avivba.simbanyu.ui.shared.base.BaseViewModel
import com.avivba.simbanyu.ui.shared.model.UiSubscriber
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.launch

class MapViewModel(
    private val subscriberRepository: SubscriberRepository,
    private val refreshEventBus: RefreshEventBus
) : BaseViewModel<MapUiState, MapUiAction, MapUiEvent>(
    MapUiState()
) {
    init {
        viewModelScope.launch {
            refreshEventBus.refreshEvent
                .onStart { emit(Unit) }
                .collect {
                    load(isInit = true)
                }
        }
    }

    override fun onAction(action: MapUiAction) {
        viewModelScope.launch {
            when (action) {
                is MapUiAction.SelectSubscriber -> {
                    updateState {
                        it.copy(
                            selectedSubscriber = action.subscriber
                        )
                    }
                }

                is MapUiAction.SetLocationPermission -> {
                    updateState {
                        it.copy(
                            hasLocationPermission = action.isGranted
                        )
                    }
                }

                MapUiAction.UnselectSubscriber -> {
                    updateState {
                        it.copy(
                            selectedSubscriber = null,
                        )
                    }
                }

                is MapUiAction.GoToRecordMeter -> {
                    sendEvent(GoToRecordMeter(action.subscriber.id))
                }

                MapUiAction.HasInitialZoomed -> {
                    updateState {
                        it.copy(
                            hasInitialZoomed = true
                        )
                    }
                }
            }
        }
    }

    private suspend fun load(isInit: Boolean = false) {
        if (state.value.isLoading && !isInit) return
        updateState {
            it.copy(
                isLoading = true
            )
        }

        fetchData()

        updateState {
            it.copy(
                isLoading = false,
            )
        }
    }

    private suspend fun fetchData() {
        when (val subscribers = subscriberRepository.getAll()) {
            is Result.Success -> {
                updateState {
                    it.copy(
                        subscribers = subscribers.data.map {
                            UiSubscriber.fromDomain(it)
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
