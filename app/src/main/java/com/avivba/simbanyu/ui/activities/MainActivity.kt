package com.avivba.simbanyu.ui.activities

import android.Manifest
import android.graphics.Color
import android.os.Build
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.SystemBarStyle
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.key
import androidx.compose.ui.graphics.toArgb
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.avivba.simbanyu.domain.model.User
import com.avivba.simbanyu.ui.components.loadingbox.LoadingBox
import com.avivba.simbanyu.ui.shared.Screen
import com.avivba.simbanyu.ui.shared.SimbanyuApp
import com.avivba.simbanyu.ui.theme.Blue30
import com.avivba.simbanyu.ui.theme.SimbanyuTheme
import com.google.accompanist.permissions.ExperimentalPermissionsApi
import com.google.accompanist.permissions.isGranted
import com.google.accompanist.permissions.rememberPermissionState
import org.koin.androidx.viewmodel.ext.android.viewModel

class MainActivity : ComponentActivity() {
    val viewModel: MainActivityViewModel by viewModel()

    @OptIn(ExperimentalPermissionsApi::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge(
            statusBarStyle = SystemBarStyle.light(
                scrim = Color.TRANSPARENT,
                darkScrim = Blue30.toArgb()
            )
        )
        setContent {
            val isCheckingAuth by viewModel.isCheckingAuth.collectAsStateWithLifecycle()
            val isLoggedIn by viewModel.isLoggedIn.collectAsStateWithLifecycle()
            val profile by viewModel.profile.collectAsStateWithLifecycle()

            SimbanyuTheme {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
                    val notificationPermissionState = rememberPermissionState(
                        Manifest.permission.POST_NOTIFICATIONS
                    )

                    LaunchedEffect(Unit) {
                        if (!notificationPermissionState.status.isGranted) {
                            notificationPermissionState.launchPermissionRequest()
                        }
                    }
                }

                LoadingBox(
                    isLoading = isCheckingAuth
                ) {
                    val startScreen = when {
                        isLoggedIn && profile != null -> {
                            when (profile?.role) {
                                User.UserRole.OPERATOR.name -> Screen.OperatorMain
                                User.UserRole.SUBSCRIBER.name -> Screen.Main()
                                else -> Screen.Login
                            }
                        }

                        else -> Screen.Login
                    }

                    key(isLoggedIn) {
                        SimbanyuApp(
                            startScreen = startScreen,
                            userRole = profile?.role,
                            authEventFlow = viewModel.authEvents
                        )
                    }
                }
            }
        }
    }
}