package com.avivba.simbanyu.ui.screens.paymentsuccess

import androidx.activity.compose.BackHandler
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.airbnb.lottie.compose.LottieAnimation
import com.airbnb.lottie.compose.LottieCompositionSpec
import com.airbnb.lottie.compose.LottieConstants
import com.airbnb.lottie.compose.rememberLottieComposition
import com.avivba.simbanyu.R
import com.avivba.simbanyu.ui.components.buttons.SimbanyuButton
import com.avivba.simbanyu.ui.components.text.SimbanyuText
import com.avivba.simbanyu.ui.theme.SimbanyuTheme
import com.avivba.simbanyu.ui.utils.ObserveAsEvent
import org.koin.compose.viewmodel.koinViewModel

@Composable
fun PaymentSuccessScreen(
    modifier: Modifier = Modifier,
    viewModel: PaymentSuccessViewModel = koinViewModel(),
    onNavigateToBillHistory: () -> Unit
) {
    val state by viewModel.state.collectAsStateWithLifecycle()

    BackHandler {
        viewModel.onAction(PaymentSuccessAction.NavigateBack)
    }

    ObserveAsEvent(viewModel.event) {
        when (it) {
            PaymentSuccessEvent.NavigateBack -> {
                onNavigateToBillHistory()
            }
        }
    }

    PaymentSuccessContent(
        modifier = modifier,
        state = state,
        onAction = viewModel::onAction
    )
}

@Composable
fun PaymentSuccessContent(
    modifier: Modifier = Modifier,
    state: PaymentSuccessUiState,
    onAction: (PaymentSuccessAction) -> Unit = {}
) {
    val composition by rememberLottieComposition(LottieCompositionSpec.RawRes(R.raw.success))

    Scaffold(
        modifier = modifier,
        containerColor = Color.Transparent
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .padding(SimbanyuTheme.padding.large),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            LottieAnimation(
                modifier = Modifier.height(200.dp),
                composition = composition,
                iterations = LottieConstants.IterateForever,
            )

            SimbanyuText(
                text = "Pembayaran Berhasil!",
                style = SimbanyuTheme.typography.displayMedium,
                color = SimbanyuTheme.colors.brand,
                textAlign = TextAlign.Center
            )

            Spacer(modifier = Modifier.height(16.dp))

            SimbanyuText(
                text = "Terima kasih telah melakukan pembayaran tepat waktu.",
                style = SimbanyuTheme.typography.bodySmall,
                textAlign = TextAlign.Center
            )

            Spacer(modifier = Modifier.height(32.dp))

            SimbanyuButton(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(
                        brush = SimbanyuTheme.colors.brush.brandVertical,
                        shape = SimbanyuTheme.shapes.extraLarge
                    ),
                text = "Kembali",
                onClick = {
                    onAction(PaymentSuccessAction.NavigateBack)
                }
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun PaymentSuccessPreview() {
    SimbanyuTheme {
        PaymentSuccessContent(
            state = PaymentSuccessUiState()
        )
    }
}
