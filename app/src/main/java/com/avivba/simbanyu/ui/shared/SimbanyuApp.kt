package com.avivba.simbanyu.ui.shared

import android.widget.Toast
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.platform.LocalContext
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navDeepLink
import androidx.navigation.toRoute
import com.avivba.simbanyu.domain.model.User
import com.avivba.simbanyu.domain.repository.AuthEvent
import kotlin.reflect.typeOf
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
import com.avivba.simbanyu.ui.screens.paymentsuccess.PaymentSuccessScreen
import com.avivba.simbanyu.ui.utils.ObserveAsEvent
import kotlinx.coroutines.flow.SharedFlow

@Composable
fun SimbanyuApp(
    navHostController: NavHostController = rememberNavController(),
    startScreen: Screen,
    userRole: String? = null,
    authEventFlow: SharedFlow<AuthEvent>
) {
    val context = LocalContext.current

    ObserveAsEvent(authEventFlow) { event ->
        when (event) {
            AuthEvent.SESSION_EXPIRED -> {
                Toast.makeText(
                    context,
                    "Sesi telah berakhir, silahkan login kembali",
                    Toast.LENGTH_LONG
                ).show()
                navHostController.navigate(Screen.Login)
            }
        }
    }

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
                onNavigateBack = {
                    navHostController.popBackStack()
                }
            )
        }

        composable<Screen.Main>(
            typeMap = mapOf(
                typeOf<Screen.MainScreen>() to serializableNavType<Screen.MainScreen>()
            ),
            deepLinks = listOf(
                navDeepLink { uriPattern = "https://simbanyu.avivba.com" }
            )
        ) { backStackEntry ->
            val screen = backStackEntry.toRoute<Screen.Main>()
            if (userRole == User.UserRole.OPERATOR.name) {
                LaunchedEffect(Unit) {
                    navHostController.navigate(Screen.OperatorMain) {
                        popUpTo(Screen.Main()) { inclusive = true }
                    }
                }
            } else {
                MainScreen(
                    tab = screen.tab,
                    onNavigate = { dest ->
                        navHostController.navigate(dest)
                    },
                    onLogout = {
                        navHostController.navigate(Screen.Login) {
                            popUpTo(navHostController.graph.id) { inclusive = true }
                        }
                    })
            }
        }

        composable<Screen.Invoice>(
            typeMap = mapOf(
                typeOf<List<String>>() to serializableNavType<List<String>>()
            )
        ) {
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

        composable<Screen.PaymentSuccess>(
            deepLinks = listOf(
                navDeepLink { uriPattern = "https://simbanyu.avivba.com/payment-success" }
            )
        ) {
            if (userRole == User.UserRole.OPERATOR.name) {
                LaunchedEffect(Unit) {
                    navHostController.navigate(Screen.OperatorMain) {
                        popUpTo(Screen.PaymentSuccess) { inclusive = true }
                    }
                }
            } else {
                PaymentSuccessScreen(
                    onNavigateToBillHistory = {
                        navHostController.navigate(Screen.Main(Screen.MainScreen.BillHistory)) {
                            popUpTo(Screen.PaymentSuccess) { inclusive = true }
                        }
                    }
                )
            }
        }

        // OPERATOR

        composable<Screen.OperatorMain> {
            OperatorMainScreen(
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