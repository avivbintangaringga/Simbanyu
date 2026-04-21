package com.avivba.simbanyu.ui.screens.operator.main

import com.avivba.simbanyu.ui.shared.Screen
import com.avivba.simbanyu.ui.shared.model.Notification

data class OperatorMainUiState(
    val selectedScreen: Screen = Screen.OperatorMainScreen.Home,
    val notifications: List<Notification> = emptyList(),
    val isShowNotification: Boolean = false,
    val isShowNotificationDot: Boolean = false
)

sealed interface OperatorMainAction {
    data class ChangeScreen(val screen: Screen) : OperatorMainAction
    data object ShowNotifications : OperatorMainAction
    data object HideNotifications : OperatorMainAction
    data object MarkAllAsRead : OperatorMainAction
    data class MarkAsRead(val notification: Notification): OperatorMainAction
}

sealed interface OperatorMainEvent {
    data class ChangeScreen(val screen: Screen) : OperatorMainEvent
}

val fakeNotifications = (1..15).map { i ->
    Notification(
        id = i.toString(),
        title = "Notification $i",
        body = "This is the body of notification $i",
        date = "2023-10-${10 + i}",
        isRead = i % 2 == 0
    )
}