package com.avivba.simbanyu.ui.screens.operator.main

import com.avivba.simbanyu.ui.shared.Screen
import com.avivba.simbanyu.ui.shared.model.UiNotification

data class OperatorMainUiState(
    val selectedScreen: Screen = Screen.OperatorMainScreen.Home,
    val notifications: List<UiNotification> = emptyList(),
    val isShowNotification: Boolean = false,
    val isShowNotificationDot: Boolean = false
)

sealed interface OperatorMainAction {
    data class ChangeScreen(val screen: Screen) : OperatorMainAction
    data object ShowNotifications : OperatorMainAction
    data object HideNotifications : OperatorMainAction
    data object MarkAllAsRead : OperatorMainAction
    data class MarkAsRead(val notification: UiNotification): OperatorMainAction
}

sealed interface OperatorMainEvent {
    data class ChangeScreen(val screen: Screen) : OperatorMainEvent
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