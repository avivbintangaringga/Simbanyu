package com.avivba.simbanyu.ui.screens.main

import kotlinx.serialization.Serializable

@Serializable
sealed interface Screen {
    @Serializable
    data object Login : Screen

    @Serializable
    data object Main : Screen

    @Serializable
    data object Notifications : Screen

    @Serializable
    data class Invoice(val ids: List<String>) : Screen


    @Serializable
    sealed interface MainScreen : Screen {

        @Serializable
        data object Home : MainScreen

        @Serializable
        data object UsageHistory : MainScreen

        @Serializable
        data object BillHistory : MainScreen

        @Serializable
        data object Report : MainScreen

        @Serializable
        data object Profile : MainScreen

    }

    @Serializable
    data class BillDetails(val id: String) : Screen

}