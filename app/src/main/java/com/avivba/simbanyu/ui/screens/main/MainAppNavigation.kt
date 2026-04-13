package com.avivba.simbanyu.ui.screens.main

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.avivba.simbanyu.ui.screens.login.LoginScreen

@Composable
fun MainAppNavigation(
    modifier: Modifier = Modifier
) {
    val navController = rememberNavController()

    NavHost(
        modifier = modifier,
        navController = navController,
        startDestination = Screen.Login
    ) {
        composable<Screen.Login> {
            LoginScreen(
                onLoginSuccess = {
                    navController.popBackStack()
                    navController.navigate(Screen.Main)
                }
            )
        }

        composable<Screen.Main> {
            MainScreen()
        }
    }
}