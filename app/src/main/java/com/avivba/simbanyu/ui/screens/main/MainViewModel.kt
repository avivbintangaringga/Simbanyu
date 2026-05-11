package com.avivba.simbanyu.ui.screens.main

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.viewModelScope
import androidx.navigation.toRoute
import com.avivba.simbanyu.domain.base.Result
import com.avivba.simbanyu.domain.repository.NotificationRepository
import com.avivba.simbanyu.domain.repository.RefreshEventBus
import com.avivba.simbanyu.ui.shared.NavTypeMaps
import com.avivba.simbanyu.ui.shared.Screen
import com.avivba.simbanyu.ui.shared.base.BaseViewModel
import com.avivba.simbanyu.ui.shared.model.UiNotification
import kotlinx.coroutines.launch

class MainViewModel(
    private val notificationRepository: NotificationRepository,
    private val refreshEventBus: RefreshEventBus,
    savedStateHandle: SavedStateHandle
) : BaseViewModel<MainUiState, MainAction, MainEvent>(
    MainUiState(
        selectedScreen = savedStateHandle.toRoute<Screen.Main>(NavTypeMaps.Main).tab
    )
) {
    init {
        val initialTab = savedStateHandle.toRoute<Screen.Main>(NavTypeMaps.Main).tab
        viewModelScope.launch {
            sendEvent(MainEvent.ChangeScreen(initialTab))
        }

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

        viewModelScope.launch {
            notificationRepository.markAsRead(notification.id)
        }
    }

    private fun markAllAsRead() {
        val unreadIds = state.value.notifications.filter { !it.isRead }.map { it.id }
        if (unreadIds.isEmpty()) return

        updateState {
            it.copy(
                notifications = it.notifications.map { n ->
                    n.copy(isRead = true)
                }
            )
        }

        viewModelScope.launch {
            notificationRepository.markAllAsRead(unreadIds)
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
            }
        }
    }
}