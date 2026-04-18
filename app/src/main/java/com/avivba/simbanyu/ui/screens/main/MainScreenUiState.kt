package com.avivba.simbanyu.ui.screens.main

data class MainUiState(
    val selectedScreen: Screen = Screen.MainScreen.Home,
)

sealed interface MainAction {
    data class ChangeScreen(val screen: Screen) : MainAction
    data object ShowNotifications : MainAction
}

sealed interface MainEvent {

}