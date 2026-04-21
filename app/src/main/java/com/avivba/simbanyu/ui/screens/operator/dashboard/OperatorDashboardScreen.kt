package com.avivba.simbanyu.ui.screens.operator.dashboard

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.avivba.simbanyu.ui.core.Screen
import com.avivba.simbanyu.ui.utils.ObserveAsEvent
import org.koin.compose.viewmodel.koinViewModel

@Composable
fun OperatorDashboardScreen(
    viewModel: OperatorDashboardViewModel = koinViewModel(),
    onNavigate: (Screen) -> Unit = {}
) {
    val state by viewModel.state.collectAsStateWithLifecycle()

    ObserveAsEvent(viewModel.event) {
        when (it) {
            is OperatorDashboardEvent.ShowError -> {}
        }
    }

    OperatorDashboardContent(
        state = state,
        onAction = viewModel::onAction
    )
}

@Composable
fun OperatorDashboardContent(
    state: OperatorDashboardUiState,
    onAction: (OperatorDashboardAction) -> Unit = {}
) {

}

@Preview(showBackground = true)
@Composable
fun OperatorDashboardPreview() {
    OperatorDashboardContent(
        state = OperatorDashboardUiState()
    )
}