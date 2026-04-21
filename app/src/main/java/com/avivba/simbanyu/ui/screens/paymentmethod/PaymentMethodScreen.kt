package com.avivba.simbanyu.ui.screens.paymentmethod

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Devices
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.avivba.simbanyu.R
import com.avivba.simbanyu.ui.components.buttons.SimbanyuButton
import com.avivba.simbanyu.ui.components.item.PaymentMethodItem
import com.avivba.simbanyu.ui.components.text.SimbanyuText
import com.avivba.simbanyu.ui.components.topbar.TopBar
import com.avivba.simbanyu.ui.core.Screen
import com.avivba.simbanyu.ui.theme.SimbanyuTheme
import com.avivba.simbanyu.ui.utils.ObserveAsEvent
import org.koin.compose.viewmodel.koinViewModel

@Composable
fun PaymentMethodScreen(
    modifier: Modifier = Modifier,
    viewModel: PaymentMethodViewModel = koinViewModel(),
    onNavigate: (Screen) -> Unit = {},
    onNavigateBack: () -> Unit = {}
) {
    val state by viewModel.state.collectAsStateWithLifecycle()

    ObserveAsEvent(viewModel.event) {
        when (it) {
            is PaymentMethodUiEvent.GoBack -> {
                onNavigateBack()
            }
        }
    }

    PaymentMethodContent(
        modifier = modifier,
        state = state,
        onAction = viewModel::onAction
    )
}

@Composable
fun PaymentMethodContent(
    modifier: Modifier = Modifier,
    state: PaymentMethodUiState,
    onAction: (PaymentMethodUiAction) -> Unit = {}
) {
    val scrollState = rememberScrollState()
    Scaffold(
        modifier = modifier,
        containerColor = Color.Transparent,
        topBar = {
            TopBar(
                text = "Pilih Metode Pembayaran",
                navigationIcon = {
                    Icon(
                        painter = painterResource(R.drawable.ic_arrow_backward),
                        contentDescription = null,
                        modifier = Modifier
                            .fillMaxHeight()
                            .padding(
                                vertical = SimbanyuTheme.padding.small,
                                horizontal = SimbanyuTheme.padding.medium
                            )
                            .clip(CircleShape)
                            .clickable {
                                onAction(PaymentMethodUiAction.GoBack)
                            }
                            .padding(
                                SimbanyuTheme.padding.large
                            )
                    )
                }
            )
        },
        bottomBar = {
            SimbanyuButton(
                modifier = Modifier
                    .padding(
                        horizontal = SimbanyuTheme.padding.large
                    )
                    .padding(
                        bottom = SimbanyuTheme.padding.extraLarge
                    )
                    .fillMaxWidth()
                    .background(
                        brush = SimbanyuTheme.colors.brush.brandVertical,
                        shape = SimbanyuTheme.shapes.extraLarge
                    ),
                text = "Pilih Pembayaran",
                onClick = {
                    onAction(PaymentMethodUiAction.Choose)
                },
                iconLeft = {
                    Icon(
                        painter = painterResource(R.drawable.ic_check_circle),
                        contentDescription = null,
                        tint = SimbanyuTheme.colors.brandText
                    )
                }
            )
        }
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .padding(SimbanyuTheme.padding.large)
                .verticalScroll(scrollState),
            verticalArrangement = Arrangement.spacedBy(SimbanyuTheme.spacing.medium)
        ) {
            listOf(
                Pair(
                    "E-Wallet",
                    state.eWallet
                ),
                Pair(
                    "Bank Transfer",
                    state.bankTransfer
                ),
                Pair(
                    "Retail",
                    state.retail
                )
            ).forEach { types ->
                if (types.second.isNotEmpty()) {
                    SimbanyuText(
                        modifier = Modifier
                            .padding(
                                top = SimbanyuTheme.padding.medium,
                            ),
                        text = types.first,
                        style = SimbanyuTheme.typography.titleSection
                    )

                    types.second.forEach { method ->
                        PaymentMethodItem(
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(IntrinsicSize.Min),
                            method = method,
                            selected = state.selectedPaymentMethod == method,
                            onClick = {
                                onAction(PaymentMethodUiAction.SelectPaymentMethod(method))
                            }
                        )
                    }
                }
            }
        }
    }
}

@Preview(showBackground = true, device = Devices.PIXEL_7, showSystemUi = false)
@Composable
fun PaymentMethodPreview() {
    PaymentMethodContent(
        state = PaymentMethodUiState(
            isLoading = false,
            selectedPaymentMethod = null,
            eWallet = fakeEWallet,
            bankTransfer = fakeBankTransfer,
            retail = fakeRetail
        )
    )
}