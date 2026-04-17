package com.avivba.simbanyu.ui.screens.main

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.avivba.simbanyu.ui.components.bottombar.BottomBar
import com.avivba.simbanyu.ui.components.topbar.TopBar
import com.avivba.simbanyu.ui.screens.dashboard.DashboardContent
import com.avivba.simbanyu.ui.screens.dashboard.DashboardScreen

@Preview(showBackground = true)
@Composable
fun MainScreen(
    modifier: Modifier = Modifier,
    onNavigate: (Screen) -> Unit = {},
    navController: NavHostController = rememberNavController()
) {
    Scaffold(
        modifier = modifier,
        containerColor = Color.Transparent,
        bottomBar = {
            BottomBar()
        },
        topBar = {
            TopBar()
        }
    ) { paddingValues ->
        NavHost(
            modifier = Modifier.padding(
                paddingValues
            ),
            navController = navController,
            startDestination = Screen.MainScreen.Home
        ) {
            composable<Screen.MainScreen.Home> {
                DashboardScreen(
                    onNavigate = onNavigate
                )
            }
        }
    }
}