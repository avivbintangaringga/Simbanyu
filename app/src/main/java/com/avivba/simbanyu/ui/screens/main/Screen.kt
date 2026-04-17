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
        object UsageHistory : MainScreen()

        @Serializable
        object BillHistory : MainScreen()

        @Serializable
        object Report : MainScreen()

        @Serializable
        object Profile : MainScreen()

    }

    @Serializable
    data class BillDetails(val id: String) : Screen()

    @Serializable
    object Settings : Screen()
}