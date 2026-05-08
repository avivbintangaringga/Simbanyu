package com.avivba.simbanyu.ui.screens.operator.main

import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.requiredWidth
import androidx.compose.foundation.layout.requiredWidthIn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.avivba.simbanyu.R
import com.avivba.simbanyu.di.viewModelModule
import com.avivba.simbanyu.ui.components.bottombar.BottomBar
import com.avivba.simbanyu.ui.components.bottombar.BottomBarMenuItem
import com.avivba.simbanyu.ui.components.emptystate.EmptyState
import com.avivba.simbanyu.ui.components.item.NotificationItem
import com.avivba.simbanyu.ui.components.text.SimbanyuText
import com.avivba.simbanyu.ui.components.topbar.TopBar
import com.avivba.simbanyu.ui.shared.Screen
import com.avivba.simbanyu.ui.screens.operator.dashboard.OperatorDashboardScreen
import com.avivba.simbanyu.ui.screens.operator.map.MapScreen
import com.avivba.simbanyu.ui.screens.operator.report.OperatorReportScreen
import com.avivba.simbanyu.ui.screens.operator.subsribers.SubscribersScreen
import com.avivba.simbanyu.ui.screens.profile.ProfileScreen
import com.avivba.simbanyu.ui.theme.SimbanyuTheme
import com.avivba.simbanyu.ui.utils.ObserveAsEvent
import org.koin.compose.KoinApplication
import org.koin.compose.viewmodel.koinViewModel
import org.koin.dsl.KoinConfiguration

@Composable
fun OperatorMainScreen(
    modifier: Modifier = Modifier,
    onNavigate: (Screen) -> Unit = {},
    onNavigateBack: () -> Unit = {},
    onLogout: () -> Unit,
    viewModel: OperatorMainViewModel = koinViewModel()
) {
    val state by viewModel.state.collectAsStateWithLifecycle()
    val navController = rememberNavController()

    ObserveAsEvent(viewModel.event) {
        when (it) {
            is OperatorMainEvent.ChangeScreen -> {
                navController.navigate(it.screen) {
                    popUpTo(navController.graph.id) {
                        saveState = true
                    }

                    launchSingleTop = true
                    restoreState = true
                }
            }
        }
    }

    OperatorMainScreenContent(
        modifier = modifier,
        navController = navController,
        onNavigate = onNavigate,
        onAction = viewModel::onAction,
        onLogout = onLogout,
        state = state
    )
}

private val menus = listOf(
    BottomBarMenuItem(
        title = "Home",
        iconId = R.drawable.ic_home,
        screen = Screen.OperatorMainScreen.Home
    ),
    BottomBarMenuItem(
        title = "Pelanggan",
        iconId = R.drawable.ic_group,
        screen = Screen.OperatorMainScreen.Subscribers
    ),
    BottomBarMenuItem(
        title = "Peta",
        iconId = R.drawable.ic_map,
        screen = Screen.OperatorMainScreen.Map
    ),
    BottomBarMenuItem(
        title = "Laporan",
        iconId = R.drawable.ic_feedback,
        screen = Screen.OperatorMainScreen.Report
    ),
    BottomBarMenuItem(
        title = "Profil",
        iconId = R.drawable.ic_person,
        screen = Screen.OperatorMainScreen.Profile
    )
)

@Composable
fun OperatorMainScreenContent(
    modifier: Modifier = Modifier,
    state: OperatorMainUiState,
    navController: NavHostController = rememberNavController(),
    onAction: (OperatorMainAction) -> Unit = {},
    onNavigate: (Screen) -> Unit = {},
    onLogout: () -> Unit
) {
    Scaffold(
        modifier = modifier,
        containerColor = Color.Transparent,
        bottomBar = {
            BottomBar(
                selected = state.selectedScreen,
                menus = menus,
                onSelectedMenuChanged = { menu ->
                    onAction(OperatorMainAction.ChangeScreen(menu.screen))
                })
        },
        topBar = {
            TopBar(
                text = "Simbanyu Operator",
                actions = {
                    Box(
                        modifier = Modifier,
                        contentAlignment = Alignment.Center
                    ) {
                        Icon(
                            modifier = Modifier
                                .clip(CircleShape)
                                .clickable {
                                    onAction(OperatorMainAction.ShowNotifications)
                                }
                                .padding(
                                    vertical = SimbanyuTheme.padding.large,
                                    horizontal = SimbanyuTheme.padding.large
                                ),
                            painter = painterResource(R.drawable.ic_notifications),
                            contentDescription = null,
                            tint = SimbanyuTheme.colors.textHeading
                        )

                        if (state.isShowNotificationDot) {
                            Box(
                                modifier = Modifier
                                    .offset(
                                        x = 5.dp,
                                        y = 5.dp
                                    )
                                    .requiredWidth(8.dp)
                                    .height(8.dp)
                                    .clip(CircleShape)
                                    .background(SimbanyuTheme.colors.statusNegativeText)
                            )
                        }
                    }

                    DropdownMenu(
                        expanded = state.isShowNotification,
                        onDismissRequest = {
                            onAction(OperatorMainAction.HideNotifications)
                        },
                        containerColor = SimbanyuTheme.colors.surfaceCard,
                    ) {
                        Row(
                            modifier = Modifier
                                .requiredWidthIn(
                                    min = 250.dp
                                )
                                .padding(
                                    horizontal = SimbanyuTheme.padding.medium,
                                    vertical = SimbanyuTheme.padding.small
                                ),
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.SpaceBetween
                        ) {
                            SimbanyuText(
                                modifier = Modifier,
                                text = "Notifikasi",
                                style = SimbanyuTheme.typography.titleSection,
                                color = SimbanyuTheme.colors.textHeading
                            )

                            SimbanyuText(
                                modifier = Modifier
                                    .padding(
                                        vertical = SimbanyuTheme.padding.small,
                                        horizontal = SimbanyuTheme.padding.medium
                                    )
                                    .clip(SimbanyuTheme.shapes.medium)
                                    .clickable {
                                        onAction(OperatorMainAction.MarkAllAsRead)
                                    },
                                text = "Tandai baca",
                                style = SimbanyuTheme.typography.bodyProminent,
                                color = SimbanyuTheme.colors.brand
                            )
                        }

                        Spacer(
                            Modifier
                                .height(1.dp)
                                .fillMaxWidth()
                                .background(SimbanyuTheme.colors.borderCard)
                        )

                        if (state.notifications.isNotEmpty()) {
                            Column(
                                modifier = Modifier
                                    .padding(
                                        vertical = SimbanyuTheme.padding.small
                                    ),
                            ) {
                                state.notifications.forEach { notification ->
                                    NotificationItem(
                                        notification = notification,
                                        onClick = {
                                            onAction(OperatorMainAction.MarkAsRead(notification))
                                        }
                                    )
                                }
                            }
                        } else {
                            EmptyState(
                                message = "Tidak ada notifikasi"
                            )
                        }
                    }
                }
            )
        }
    ) { paddingValues ->
        val fadeDuration = 250
        NavHost(
            modifier = Modifier.padding(
                paddingValues
            ),
            navController = navController,
            startDestination = Screen.OperatorMainScreen.Home,
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
            composable<Screen.OperatorMainScreen.Home> {
                OperatorDashboardScreen(
                    onNavigate = onNavigate,
                    onChangeTab = { tab ->
                        onAction(OperatorMainAction.ChangeScreen(tab))
                    }
                )
            }
            composable<Screen.OperatorMainScreen.Subscribers> {
                SubscribersScreen(
                    onNavigate = onNavigate
                )
            }

            composable<Screen.OperatorMainScreen.Map> {
                MapScreen(
                    onNavigate = onNavigate
                )
            }

            composable<Screen.OperatorMainScreen.Report> {
                OperatorReportScreen(
                    onNavigate = onNavigate
                )
            }

            composable<Screen.OperatorMainScreen.Profile> {
                ProfileScreen(
                    onNavigate = onNavigate,
                    onLogout = onLogout
                )
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun OperatorMainScreenPreview() {
    KoinApplication(
        configuration = KoinConfiguration {
            modules(viewModelModule)
        }
    ) {
        OperatorMainScreen(
            onLogout = {}
        )
    }
}