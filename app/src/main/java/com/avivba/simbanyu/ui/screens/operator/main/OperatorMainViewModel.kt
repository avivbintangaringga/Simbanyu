package com.avivba.simbanyu.ui.screens.operator.main

import androidx.lifecycle.viewModelScope
import com.avivba.simbanyu.ui.shared.base.BaseViewModel
import com.avivba.simbanyu.ui.shared.model.Notification
import kotlinx.coroutines.launch

class OperatorMainViewModel : BaseViewModel<OperatorMainUiState, OperatorMainAction, OperatorMainEvent>(
    OperatorMainUiState()
) {
    init {
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

        loadNotifications()
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

    private fun markAsRead(notification: Notification) {
        updateState {
            it.copy(
                notifications = it.notifications.map { n ->
                    if (n.id == notification.id) n.copy(
                        isRead = true
                    ) else n
                }
            )
        }
    }

    private fun markAllAsRead() {
        updateState {
            it.copy(
                notifications = it.notifications.map { notification ->
                    notification.copy(
                        isRead = true
                    )
                }
            )
        }
    }

    private fun loadNotifications() {
        viewModelScope.launch {
            updateState {
                it.copy(
                    notifications = fakeNotifications
                )
            }
        }
    }
}