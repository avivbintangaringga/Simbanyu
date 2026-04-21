package com.avivba.simbanyu.ui.screens.main

import androidx.lifecycle.viewModelScope
import com.avivba.simbanyu.ui.core.BaseViewModel
import kotlinx.coroutines.launch

class MainViewModel : BaseViewModel<MainUiState, MainAction, MainEvent>(
    MainUiState()
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

    override fun onAction(action: MainAction) {
        viewModelScope.launch {
            when (action) {
                is MainAction.ChangeScreen -> {
                    updateState {
                        it.copy(
                            selectedScreen = action.screen
                        )
                    }
                }

                MainAction.ShowNotifications -> {
                    updateState {
                        it.copy(
                            isShowNotification = true
                        )
                    }
                }

                MainAction.HideNotifications -> {
                    updateState {
                        it.copy(
                            isShowNotification = false
                        )
                    }
                }

                MainAction.MarkAllAsRead -> {
                    markAllAsRead()
                }

                is MainAction.MarkAsRead -> {
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