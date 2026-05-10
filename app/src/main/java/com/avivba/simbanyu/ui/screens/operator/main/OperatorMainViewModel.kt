package com.avivba.simbanyu.ui.screens.operator.main

import androidx.lifecycle.viewModelScope
import com.avivba.simbanyu.domain.base.Result
import com.avivba.simbanyu.domain.repository.NotificationRepository
import com.avivba.simbanyu.domain.repository.RefreshEventBus
import com.avivba.simbanyu.ui.shared.base.BaseViewModel
import com.avivba.simbanyu.ui.shared.model.UiNotification
import kotlinx.coroutines.launch

class OperatorMainViewModel(
    private val notificationRepository: NotificationRepository,
    private val refreshEventBus: RefreshEventBus
) : BaseViewModel<OperatorMainUiState, OperatorMainAction, OperatorMainEvent>(
    OperatorMainUiState()
) {
    init {
        viewModelScope.launch {
            loadNotifications()
        }

        viewModelScope.launch {
            state.collect { currentState ->
                val hasUnread = currentState.notifications.any { !it.isRead }
                if (currentState.isShowNotificationDot != hasUnread) {
                    updateState {
                        it.copy(
                            isShowNotificationDot = hasUnread
                        )
                    }
                }
            }
        }

        viewModelScope.launch {
            refreshEventBus.refreshEvent.collect {
                loadNotifications()
            }
        }
    }

    override fun onAction(action: OperatorMainAction) {
        viewModelScope.launch {
            when (action) {
                is OperatorMainAction.ChangeScreen -> {
                    updateState {
                        it.copy(
                            selectedScreen = action.screen
                        )
                    }
                    sendEvent(OperatorMainEvent.ChangeScreen(action.screen))
                }

                OperatorMainAction.ShowNotifications -> {
                    updateState {
                        it.copy(
                            isShowNotification = true
                        )
                    }
                }

                OperatorMainAction.HideNotifications -> {
                    updateState {
                        it.copy(
                            isShowNotification = false
                        )
                    }
                }

                OperatorMainAction.MarkAllAsRead -> {
                    markAllAsRead()
                }

                is OperatorMainAction.MarkAsRead -> {
                    markAsRead(action.notification)
                }
            }
        }
    }

    private fun markAsRead(notification: UiNotification) {
        updateState {
            it.copy(
                notifications = it.notifications.map { n ->
                    if (n.id == notification.id) n.copy(
                        isRead = true
                    ) else n
                }
            )
        }

        // TODO: Store marked id
    }

    private fun markAllAsRead() {
        state.value.notifications.filter { !it.isRead }.forEach {
            markAsRead(it)
        }
    }

    private suspend fun loadNotifications() {
        when (val notifications = notificationRepository.getAll()) {
            is Result.Success -> {
                updateState {
                    it.copy(
                        notifications = notifications.data
                            .sortedByDescending { it.date }
                            .map { UiNotification.fromDomain(it) }
                    )
                }
            }

            is Result.Error -> {
                // TODO: Handle error
            }
        }
    }
}