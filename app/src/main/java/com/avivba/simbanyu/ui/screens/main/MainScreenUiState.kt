package com.avivba.simbanyu.ui.screens.main

import com.avivba.simbanyu.ui.core.Screen

data class Notification(
    val id: String,
    val title: String,
    val body: String,
    val date: String,
    val isRead: Boolean
)

data class MainUiState(
    val selectedScreen: Screen = Screen.MainScreen.Home,
    val notifications: List<Notification> = emptyList(),
    val isShowNotification: Boolean = false,
    val isShowNotificationDot: Boolean = false
)

sealed interface MainAction {
    data class ChangeScreen(val screen: Screen) : MainAction
    data object ShowNotifications : MainAction
    data object HideNotifications : MainAction
    data object MarkAllAsRead : MainAction
    data class MarkAsRead(val notification: Notification): MainAction
}

sealed interface MainEvent {}

val fakeNotifications = (1..15).map { i ->
    Notification(
        id = i.toString(),
        title = "Notification $i",
        body = "This is the body of notification $i",
        date = "2023-10-${10 + i}",
        isRead = i % 2 == 0
    )
}