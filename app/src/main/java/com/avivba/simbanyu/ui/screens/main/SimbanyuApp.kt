package com.avivba.simbanyu.ui.screens.main

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navigation
import com.avivba.simbanyu.ui.components.background.Background
import com.avivba.simbanyu.ui.components.text.SimbanyuText
import com.avivba.simbanyu.ui.screens.login.LoginContent
import com.avivba.simbanyu.ui.screens.login.LoginScreen

@Composable
fun SimbanyuApp(
    modifier: Modifier = Modifier,
    navHostController: NavHostController = rememberNavController(),
) {
    Background()

    NavHost(
        navController = navHostController,
        startDestination = Screen.Login
    ) {
        composable<Screen.Login> {
            LoginScreen(onNavigate = { dest ->
                navHostController.navigate(dest)
            })
        }

        navigation<Screen.Main>(
            startDestination = Screen.MainScreen.Home
        ) {
            composable<Screen.MainScreen.Home> {
                MainScreen(onNavigate = { dest ->
                    navHostController.popBackStack()
                    navHostController.navigate(dest)
                })
            }
        }

        composable<Screen.Settings> {
            SimbanyuText("Settings Screen")
        }
    }
}