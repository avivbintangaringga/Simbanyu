package com.avivba.simbanyu.domain.repository

import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow


class RefreshEventBus {
    private val _refreshEvents = MutableSharedFlow<Unit>()
    val refreshEvent = _refreshEvents.asSharedFlow()

    suspend fun triggerRefresh() {
        _refreshEvents.emit(Unit)
    }
}