package com.avivba.simbanyu.ui.screens.operator.subsribers

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.avivba.simbanyu.R
import com.avivba.simbanyu.ui.components.topbar.TopBar
import com.avivba.simbanyu.ui.screens.invoice.InvoiceAction
import com.avivba.simbanyu.ui.shared.Screen
import com.avivba.simbanyu.ui.theme.SimbanyuTheme
import com.avivba.simbanyu.ui.utils.ObserveAsEvent
import org.koin.compose.viewmodel.koinViewModel

@Composable
fun SubscribersScreen(
    modifier: Modifier = Modifier,
    viewModel: SubscribersViewModel = koinViewModel(),
    onNavigate: (Screen) -> Unit = {},
) {
    val state by viewModel.state.collectAsStateWithLifecycle()

    ObserveAsEvent(viewModel.event) {
        when (it) {
            is SubscribersEvent.GoToSubscriberDetails -> {
                onNavigate(Screen.SubscriberDetails(it.subscriber.id))
            }
        }
    }

    SubscribersContent(
        modifier = modifier,
        state = state,
        onAction = viewModel::onAction,
        onNavigate = onNavigate
    )
}

@Composable
fun SubscribersContent(
    modifier: Modifier = Modifier,
    state: SubscribersUiState,
    onAction: (SubscribersAction) -> Unit = {},
    onNavigate: (Screen) -> Unit = {}
) {
}

@Preview(showBackground = true)
@Composable
private fun SubsribersContentPreview() {
    SubscribersContent(
        state = SubscribersUiState()
    )
}