package com.avivba.simbanyu.ui.shared

import kotlinx.serialization.Serializable

@Serializable
sealed interface Screen {
    @Serializable
    data object Login : Screen

    @Serializable
    data object Main : Screen

    @Serializable
    data object OperatorMain : Screen

    @Serializable
    data class Invoice(val ids: List<String>) : Screen

    @Serializable
    data object PaymentMethods : Screen

    @Serializable
    data class BillDetails(val id: String) : Screen

    @Serializable
    data class SubscriberDetails(val id: String) : Screen

    @Serializable
    data class RecordMeter(val id: String) : Screen

    @Serializable
    data object AnnouncementScreen : Screen

    @Serializable
    data object OperatorAnnouncementScreen : Screen

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
    sealed interface OperatorMainScreen : Screen {
        @Serializable
        data object Home : OperatorMainScreen

        @Serializable
        data object Subscribers : OperatorMainScreen

        @Serializable
        data object Map : OperatorMainScreen

        @Serializable
        data object Report : OperatorMainScreen

        @Serializable
        data object Profile : OperatorMainScreen
    }

}