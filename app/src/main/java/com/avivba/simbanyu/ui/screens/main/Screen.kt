package com.avivba.simbanyu.ui.screens.main

import kotlinx.serialization.Serializable

sealed interface Screen {
    @Serializable
    data object Login : Screen

    @Serializable
    data object Main : Screen

    @Serializable
    data object Profile : Screen

    @Serializable
    data object Settings : Screen
}