package com.avivba.simbanyu.ui.screens.main

import com.avivba.simbanyu.ui.shared.Screen
import com.avivba.simbanyu.ui.shared.model.UiNotification


data class MainUiState(
    val selectedScreen: Screen = Screen.MainScreen.Home,
    val notifications: List<UiNotification> = emptyList(),
    val isShowNotification: Boolean = false,
    val isShowNotificationDot: Boolean = false
)

sealed interface MainAction {
    data class ChangeScreen(val screen: Screen) : MainAction
    data object ShowNotifications : MainAction
    data object HideNotifications : MainAction
    data object MarkAllAsRead : MainAction
    data class MarkAsRead(val notification: UiNotification): MainAction
}

sealed interface MainEvent {
    data class ChangeScreen(val screen: Screen) : MainEvent
}

val fakeNotifications = (1..15).map { i ->
    UiNotification(
        id = i.toString(),
        title = "Notification $i",
        body = "This is the body of notification $i",
        date = "2023-10-${10 + i}",
        isRead = i % 2 == 0
    )
}