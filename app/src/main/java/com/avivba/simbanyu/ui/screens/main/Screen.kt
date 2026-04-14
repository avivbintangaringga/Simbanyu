package com.avivba.simbanyu.ui.screens.main

import kotlinx.serialization.Serializable

@Serializable
sealed class Screen {
    @Serializable
    object Login : Screen()

    @Serializable
    object Main : Screen()

    @Serializable
    sealed class MainScreen : Screen() {

        @Serializable
        object Home : MainScreen()

        @Serializable
        object Profile : MainScreen()

        @Serializable
        object Settings : MainScreen()
    }

    @Serializable
    object Settings : Screen()
}