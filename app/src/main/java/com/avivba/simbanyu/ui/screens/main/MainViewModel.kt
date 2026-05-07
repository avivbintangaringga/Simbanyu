package com.avivba.simbanyu.ui.screens.main

import androidx.lifecycle.viewModelScope
import com.avivba.simbanyu.domain.base.Result
import com.avivba.simbanyu.domain.repository.NotificationRepository
import com.avivba.simbanyu.ui.shared.base.BaseViewModel
import com.avivba.simbanyu.ui.shared.model.Notification
import kotlinx.coroutines.launch

class MainViewModel(
    private val notificationRepository: NotificationRepository
) : BaseViewModel<MainUiState, MainAction, MainEvent>(
    MainUiState()
) {
    init {
        viewModelScope.launch {
            loadNotifications()

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
                    sendEvent(MainEvent.ChangeScreen(action.screen))
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
                        notifications = notifications.data.map { Notification.fromDomain(it) }
                    )
                }
            }

            is Result.Error -> {
                // TODO: Handle error
            }
        }
    }
}