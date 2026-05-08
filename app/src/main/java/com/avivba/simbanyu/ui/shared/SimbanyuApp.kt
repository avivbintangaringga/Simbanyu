package com.avivba.simbanyu.ui.shared

import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.avivba.simbanyu.ui.components.background.Background
import com.avivba.simbanyu.ui.screens.announcement.AnnouncementScreen
import com.avivba.simbanyu.ui.screens.billdetails.BillDetailsScreen
import com.avivba.simbanyu.ui.screens.camera.CameraScreen
import com.avivba.simbanyu.ui.screens.invoice.InvoiceScreen
import com.avivba.simbanyu.ui.screens.login.LoginScreen
import com.avivba.simbanyu.ui.screens.main.MainScreen
import com.avivba.simbanyu.ui.screens.operator.announcement.OperatorAnnouncementScreen
import com.avivba.simbanyu.ui.screens.operator.main.OperatorMainScreen
import com.avivba.simbanyu.ui.screens.operator.meterrecord.MeterRecordScreen
import com.avivba.simbanyu.ui.screens.operator.subscriberdetail.SubscriberDetailsScreen
import com.avivba.simbanyu.ui.screens.paymentmethod.PaymentMethodScreen

@Composable
fun SimbanyuApp(
    navHostController: NavHostController = rememberNavController(),
    startScreen: Screen
) {
    Background()

    val fadeDuration = 250

    NavHost(
        navController = navHostController,
        startDestination = startScreen,
        enterTransition = {
            fadeIn(animationSpec = tween(fadeDuration))
        },
        exitTransition = {
            fadeOut(animationSpec = tween(fadeDuration))
        },
        popEnterTransition = {
            fadeIn(animationSpec = tween(fadeDuration))
        },
        popExitTransition = {
            fadeOut(animationSpec = tween(fadeDuration))
        }
    ) {
        composable<Screen.Login> {
            LoginScreen(onNavigate = { dest ->
                navHostController.popBackStack()
                navHostController.navigate(dest)
            })
        }

        composable<Screen.Camera> {
            CameraScreen(
                onImageCaptured = { file ->
                    navHostController.previousBackStackEntry
                        ?.savedStateHandle
                        ?.set("camera_result", file.absolutePath)
                    navHostController.popBackStack()
                },
                onNavigateBack = {
                    navHostController.popBackStack()
                }
            )
        }

        composable<Screen.Main> {
            MainScreen(
                rootNavController = navHostController,
                onNavigate = { dest ->
                    navHostController.navigate(dest)
                },
                onLogout = {
                    navHostController.navigate(Screen.Login) {
                        popUpTo(navHostController.graph.id) { inclusive = true }
                    }
                })
        }

        composable<Screen.Invoice> {
            InvoiceScreen(
                onNavigate = { dest ->
                    navHostController.navigate(dest)
                },
                onNavigateBack = {
                    navHostController.popBackStack()
                }
            )
        }

        composable<Screen.BillDetails> {
            BillDetailsScreen(
                onNavigateBack = {
                    navHostController.popBackStack()
                }
            )
        }

        composable<Screen.PaymentMethods> {
            PaymentMethodScreen(
                onNavigate = { dest ->
                    navHostController.navigate(dest)
                },
                onNavigateBack = {
                    navHostController.popBackStack()
                }
            )
        }

        // OPERATOR

        composable<Screen.OperatorMain> {
            OperatorMainScreen(
                rootNavController = navHostController,
                onNavigate = { dest ->
                    navHostController.navigate(dest)
                },
                onLogout = {
                    navHostController.navigate(Screen.Login) {
                        popUpTo(navHostController.graph.id) { inclusive = true }
                    }
                }
            )
        }

        composable<Screen.AnnouncementScreen> {
            AnnouncementScreen(
                onNavigate = { dest ->
                    navHostController.navigate(dest)
                },
                onNavigateBack = {
                    navHostController.popBackStack()
                }
            )
        }


        composable<Screen.OperatorAnnouncementScreen> {
            OperatorAnnouncementScreen(
                onNavigate = { dest ->
                    navHostController.navigate(dest)
                },
                onNavigateBack = {
                    navHostController.popBackStack()
                }
            )
        }

        composable<Screen.SubscriberDetails> {
            SubscriberDetailsScreen(
                onNavigate = { dest ->
                    navHostController.navigate(dest)
                },
                onNavigateBack = {
                    navHostController.popBackStack()
                }
            )
        }

        composable<Screen.RecordMeter> {
            MeterRecordScreen(
                onNavigate = { dest ->
                    navHostController.navigate(dest)
                },
                onNavigateBack = {
                    navHostController.popBackStack()
                }
            )
        }
    }
}