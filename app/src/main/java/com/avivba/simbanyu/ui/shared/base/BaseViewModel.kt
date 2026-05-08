package com.avivba.simbanyu.ui.shared.base

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

abstract class BaseViewModel<State, Action, Event>(
    initialState: State
) : ViewModel() {
    private val _state = MutableStateFlow(initialState)
    val state = _state.asStateFlow()

    private val _event = Channel<Event>(capacity = Channel.BUFFERED)
    val event = _event.receiveAsFlow()

    protected fun sendEvent(event: Event) {
        viewModelScope.launch {
            _event.send(event)
        }
    }

    protected fun updateState(func: (State) -> State) {
        _state.update(func)
    }

    abstract fun onAction(action: Action)
}