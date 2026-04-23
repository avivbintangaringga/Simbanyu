package com.avivba.simbanyu.ui.screens.operator.map

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.avivba.simbanyu.ui.shared.Screen
import com.avivba.simbanyu.ui.utils.ObserveAsEvent
import org.koin.compose.viewmodel.koinViewModel

@Composable
fun MapScreen(
    modifier: Modifier = Modifier,
    viewModel: MapViewModel = koinViewModel(),
    onNavigate: (Screen) -> Unit = {}
) {
    val state by viewModel.state.collectAsStateWithLifecycle()

    ObserveAsEvent(viewModel.event) {
        when (it) {
            is MapUiEvent.GoToSubscriberDetails -> {
                onNavigate(Screen.SubscriberDetails(it.subscriberId))
            }
        }
    }

    MapContent(
        modifier = modifier,
        state = state,
        onAction = viewModel::onAction
    )
}

@Composable
private fun MapContent(
    modifier: Modifier = Modifier,
    state: MapUiState,
    onAction: (MapUiAction) -> Unit = {}
) {

}

@Preview(showBackground = true)
@Composable
private fun MapContentPreview() {
    MapContent(
        state = fakeMapUiState
    )
}