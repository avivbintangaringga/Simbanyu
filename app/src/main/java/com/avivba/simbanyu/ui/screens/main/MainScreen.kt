package com.avivba.simbanyu.ui.screens.main

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.avivba.simbanyu.di.viewModelModule
import com.avivba.simbanyu.ui.components.bottombar.BottomBar
import com.avivba.simbanyu.ui.components.text.SimbanyuText
import com.avivba.simbanyu.ui.components.topbar.TopBar
import com.avivba.simbanyu.ui.screens.billhistory.BillHistoryScreen
import com.avivba.simbanyu.ui.screens.dashboard.DashboardContent
import com.avivba.simbanyu.ui.screens.dashboard.DashboardScreen
import com.avivba.simbanyu.ui.screens.dashboard.DashboardViewModel
import com.avivba.simbanyu.ui.theme.SimbanyuTheme
import org.koin.compose.KoinApplication
import org.koin.compose.koinInject
import org.koin.dsl.KoinConfiguration

@Composable
fun MainScreen(
    modifier: Modifier = Modifier,
    onNavigate: (Screen) -> Unit = {},
) {
    //TODO: TEMP!
    var selectedScreen by remember {
        mutableStateOf<Screen>(Screen.MainScreen.Home)
    }

    val navController = rememberNavController()

    Scaffold(
        modifier = modifier,
        containerColor = Color.Transparent,
        bottomBar = {
            BottomBar(
                selected = selectedScreen,
                onSelectedMenuChanged = { menu ->
                    selectedScreen = menu.screen
                    // TODO: FIX BACKSTACK
                    navController.popBackStack()
                    navController.navigate(menu.screen)
                })
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
            composable<Screen.MainScreen.UsageHistory> {
                SimbanyuText("USAGE HISTORY")
            }
            composable<Screen.MainScreen.BillHistory> {
                BillHistoryScreen(
                    onNavigate = onNavigate
                )
            }
            composable<Screen.MainScreen.Report> {
                SimbanyuText("REPORT")
            }
            composable<Screen.MainScreen.Profile> {
                SimbanyuText("PROFILE")
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun MainScreenPreview() {
    KoinApplication(
        configuration = KoinConfiguration {
            modules(viewModelModule)
        }
    ) {
        MainScreen()
    }
}