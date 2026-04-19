package com.avivba.simbanyu.ui.screens.main

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.avivba.simbanyu.R
import com.avivba.simbanyu.di.viewModelModule
import com.avivba.simbanyu.ui.components.bottombar.BottomBar
import com.avivba.simbanyu.ui.components.text.SimbanyuText
import com.avivba.simbanyu.ui.components.topbar.TopBar
import com.avivba.simbanyu.ui.screens.billhistory.BillHistoryScreen
import com.avivba.simbanyu.ui.screens.dashboard.DashboardContent
import com.avivba.simbanyu.ui.screens.dashboard.DashboardScreen
import com.avivba.simbanyu.ui.screens.dashboard.DashboardViewModel
import com.avivba.simbanyu.ui.screens.profile.ProfileScreen
import com.avivba.simbanyu.ui.screens.report.ReportScreen
import com.avivba.simbanyu.ui.screens.usagehistory.UsageHistoryScreen
import com.avivba.simbanyu.ui.theme.SimbanyuTheme
import org.koin.compose.KoinApplication
import org.koin.compose.koinInject
import org.koin.dsl.KoinConfiguration

@Composable
fun MainScreen(
    modifier: Modifier = Modifier,
    onNavigate: (Screen) -> Unit = {},
    viewModel: MainViewModel = koinInject()
) {
    val state by viewModel.state.collectAsStateWithLifecycle()

    MainScreenContent(
        modifier = modifier,
        onNavigate = onNavigate,
        onAction = viewModel::onAction,
        state = state
    )
}


@Composable
fun MainScreenContent(
    modifier: Modifier = Modifier,
    state: MainUiState,
    onAction: (MainAction) -> Unit = {},
    onNavigate: (Screen) -> Unit = {},
) {
    val navController = rememberNavController()

    Scaffold(
        modifier = modifier,
        containerColor = Color.Transparent,
        bottomBar = {
            BottomBar(
                selected = state.selectedScreen,
                onSelectedMenuChanged = { menu ->
                    onAction(MainAction.ChangeScreen(menu.screen))
                    navController.navigate(menu.screen) {
                        popUpTo(navController.graph.id) {
                            saveState = true
                        }

                        launchSingleTop = true
                        restoreState = true
                    }
                })
        },
        topBar = {
            TopBar(
                actions = {
                    Icon(
                        modifier = Modifier
                            .clip(CircleShape)
                            .clickable {
                                // TODO: Show notifications popup
                            }
                            .padding(horizontal = SimbanyuTheme.padding.large),
                        painter = painterResource(R.drawable.ic_notifications),
                        contentDescription = null,
                        tint = SimbanyuTheme.colors.textHeading
                    )
                }
            )
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
                UsageHistoryScreen(
                    onNavigate = onNavigate
                )
            }
            composable<Screen.MainScreen.BillHistory> {
                BillHistoryScreen(
                    onNavigate = onNavigate
                )
            }
            composable<Screen.MainScreen.Report> {
                ReportScreen(
                    onNavigate = onNavigate
                )
            }
            composable<Screen.MainScreen.Profile> {
                ProfileScreen(
                    onNavigate = onNavigate
                )
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