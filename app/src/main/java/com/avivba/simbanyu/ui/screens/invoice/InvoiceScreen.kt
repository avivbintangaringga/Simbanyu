package com.avivba.simbanyu.ui.screens.invoice

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.dropShadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.shadow.Shadow
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.avivba.simbanyu.R
import com.avivba.simbanyu.ui.components.buttons.SimbanyuButton
import com.avivba.simbanyu.ui.components.text.SimbanyuText
import com.avivba.simbanyu.ui.components.topbar.TopBar
import com.avivba.simbanyu.ui.core.Screen
import com.avivba.simbanyu.ui.theme.SimbanyuTheme
import com.avivba.simbanyu.ui.utils.ObserveAsEvent
import com.avivba.simbanyu.ui.utils.leftBorder
import org.koin.compose.viewmodel.koinViewModel

@Composable
fun InvoiceScreen(
    viewModel: InvoiceViewModel = koinViewModel(),
    onNavigate: (Screen) -> Unit = {},
    onNavigateBack: () -> Unit = {}
) {
    val state by viewModel.state.collectAsStateWithLifecycle()

    ObserveAsEvent(viewModel.event) {
        when (it) {
            InvoiceEvent.NavigateBack -> {
                onNavigateBack()
            }

            InvoiceEvent.GoToPaymentMethod -> {
                onNavigate(Screen.PaymentMethods)
            }
        }
    }

    InvoiceContent(
        state = state,
        onAction = viewModel::onAction,
    )
}

@Composable
fun InvoiceContent(
    modifier: Modifier = Modifier,
    state: InvoiceUiState,
    onAction: (InvoiceAction) -> Unit = {},
) {
    Scaffold(
        modifier = modifier,
        containerColor = Color.Transparent,
        topBar = {
            TopBar(
                text = "Bayar tagihan",
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
                                onAction(InvoiceAction.NavigateBack)
                            }
                            .padding(
                                SimbanyuTheme.padding.large
                            )
                    )
                }
            )
        },
        bottomBar = {
            Row(
                modifier = modifier
                    .fillMaxWidth()
                    .dropShadow(
                        shape = SimbanyuTheme.shapes.bottomBar,
                        shadow = Shadow(
                            radius = 8.dp,
                            alpha = 0.2f
                        )
                    )
                    .background(
                        color = SimbanyuTheme.colors.surfaceCard,
                        shape = SimbanyuTheme.shapes.bottomBar
                    )
                    .padding(
                        horizontal = SimbanyuTheme.padding.extraLarge,
                        vertical = SimbanyuTheme.padding.extraLarge
                    ),
            ) {
                Column(
                    modifier = Modifier
                        .weight(1f),
                ) {
                    SimbanyuText(
                        text = "Total pembayaran",
                        style = SimbanyuTheme.typography.bodySmall
                    )
                    SimbanyuText(
                        text = state.totalAmount,
                        style = SimbanyuTheme.typography.displayMedium,
                        color = SimbanyuTheme.colors.brand
                    )

                }

                SimbanyuButton(
                    modifier = Modifier
                        .height(IntrinsicSize.Min)
                        .background(
                            brush = SimbanyuTheme.colors.brush.brandVertical,
                            shape = SimbanyuTheme.shapes.large
                        ),
                    text = "Bayar",
                    onClick = {
                        onAction(InvoiceAction.Pay)
                    },
                    iconLeft = {
                        Icon(
                            painter = painterResource(R.drawable.ic_payment),
                            contentDescription = null,
                            tint = SimbanyuTheme.colors.brandText
                        )
                    }
                )
            }
        }

    ) { paddingValues ->
        LazyColumn(
            modifier = Modifier
                .padding(paddingValues)
                .padding(
                    horizontal = SimbanyuTheme.padding.large,
                    vertical = SimbanyuTheme.padding.medium
                ),
            verticalArrangement = Arrangement.spacedBy(
                SimbanyuTheme.spacing.large
            ),
        ) {
            item {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(
                            top = SimbanyuTheme.padding.medium,
                        ),
                    verticalArrangement = Arrangement.spacedBy(
                        SimbanyuTheme.spacing.medium
                    )
                ) {
                    SimbanyuText(
                        text = state.name,
                        style = SimbanyuTheme.typography.displayMedium,
                        color = SimbanyuTheme.colors.brand
                    )

                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.spacedBy(
                            SimbanyuTheme.spacing.medium
                        )
                    ) {
                        SimbanyuText(
                            modifier = Modifier
                                .background(
                                    color = SimbanyuTheme.colors.brand,
                                    shape = SimbanyuTheme.shapes.medium
                                )
                                .padding(
                                    vertical = SimbanyuTheme.padding.extraSmall,
                                    horizontal = SimbanyuTheme.padding.medium
                                ),
                            text = "ID: ${state.meterId}",
                            style = SimbanyuTheme.typography.labelUnit,
                            color = SimbanyuTheme.colors.brandText
                        )

                        SimbanyuText(
                            modifier = Modifier
                                .background(
                                    color = SimbanyuTheme.colors.brand.copy(0.3f),
                                    shape = SimbanyuTheme.shapes.medium
                                )
                                .padding(
                                    vertical = SimbanyuTheme.padding.extraSmall,
                                    horizontal = SimbanyuTheme.padding.medium
                                ),
                            text = "GOL: ${state.category}",
                            style = SimbanyuTheme.typography.labelUnit,
                            color = SimbanyuTheme.colors.brand
                        )
                    }

                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.spacedBy(
                            SimbanyuTheme.spacing.small
                        )
                    ) {
                        Icon(
                            painter = painterResource(R.drawable.ic_location),
                            contentDescription = null,
                            tint = SimbanyuTheme.colors.brand,
                            modifier = Modifier.size(16.dp)
                        )

                        SimbanyuText(
                            modifier = Modifier,
                            text = state.address,
                            style = SimbanyuTheme.typography.labelUnit,
                        )
                    }
                }
            }

            item {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(
                            top = SimbanyuTheme.padding.medium,
                        ),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    SimbanyuText(
                        text = "Tagihan",
                        style = SimbanyuTheme.typography.titleSection,
                        modifier = Modifier.weight(1f)
                    )


                    SimbanyuText(
                        modifier = Modifier
                            .background(
                                color = SimbanyuTheme.colors.statusNegative,
                                shape = SimbanyuTheme.shapes.medium
                            )
                            .padding(
                                vertical = SimbanyuTheme.padding.extraSmall,
                                horizontal = SimbanyuTheme.padding.medium
                            ),
                        text = "${state.bills.size} periode",
                        style = SimbanyuTheme.typography.labelUnit,
                        color = SimbanyuTheme.colors.statusNegativeText
                    )
                }
            }

            items(
                items = state.bills,
                key = { it.id }
            ) { bill ->
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .clip(
                            SimbanyuTheme.shapes.medium
                        )
                        .background(
                            color = SimbanyuTheme.colors.surfaceCardAlternate,
                            shape = SimbanyuTheme.shapes.medium
                        )
                        .leftBorder(
                            width = 12.dp,
                            color = SimbanyuTheme.colors.brand,
                        )
                        .padding(
                            SimbanyuTheme.padding.extraLarge
                        ),
                ) {
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(
                                bottom = SimbanyuTheme.padding.medium
                            )
                    ) {
                        SimbanyuText(
                            text = "${bill.periodMonth} ${bill.periodYear}",
                            style = SimbanyuTheme.typography.titleSection,
                            modifier = Modifier.weight(1f)
                        )

                        Icon(
                            painter = painterResource(R.drawable.ic_contract),
                            contentDescription = null,
                            tint = SimbanyuTheme.colors.statusNeutral.copy(alpha = 0.5f)
                        )
                    }

                    bill.details.forEach { detail ->
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(vertical = SimbanyuTheme.spacing.extraSmall),
                            horizontalArrangement = Arrangement.SpaceBetween,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            SimbanyuText(
                                text = detail.name,
                                style = SimbanyuTheme.typography.bodySmall
                            )
                            SimbanyuText(
                                text = detail.amount,
                                style = SimbanyuTheme.typography.bodySmall
                            )
                        }
                    }

                    Spacer(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(vertical = SimbanyuTheme.padding.small)
                            .height(1.dp)
                            .background(
                                color = SimbanyuTheme.colors.statusNeutral.copy(alpha = 0.3f)
                            )
                    )

                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        SimbanyuText(
                            text = "Subtotal",
                            style = SimbanyuTheme.typography.bodySmall,
                            fontWeight = FontWeight.Bold
                        )
                        SimbanyuText(
                            text = bill.subtotal,
                            style = SimbanyuTheme.typography.bodyProminent,
                            color = SimbanyuTheme.colors.brand
                        )
                    }
                }
            }

//            item {
//                Column(
//                    modifier = Modifier
//                        .fillMaxWidth()
//                        .padding(vertical = SimbanyuTheme.padding.medium)
//                ) {
//                    SimbanyuText(
//                        text = "Metode Pembayaran",
//                        style = SimbanyuTheme.typography.titleSection,
//                    )
//
//                    Box(
//                        modifier = modifier
//                            .fillMaxWidth()
//                            .padding(
//                                top = SimbanyuTheme.padding.large,
//                            )
//                            .background(
//                                color = SimbanyuTheme.colors.surfaceCardAlternate,
//                                shape = SimbanyuTheme.shapes.large
//                            )
//                            .clip(
//                                shape = SimbanyuTheme.shapes.large
//                            )
//                            .clickable {
//                                onAction(InvoiceAction.SelectPaymentMethod)
//                            }
//                    ) {
//                        Row(
//                            modifier = Modifier
//                                .padding(SimbanyuTheme.padding.large),
//                            horizontalArrangement = Arrangement.spacedBy(
//                                SimbanyuTheme.spacing.large
//                            ),
//                            verticalAlignment = Alignment.CenterVertically
//                        ) {
//                            Icon(
//                                painter = painterResource(R.drawable.ic_payment),
//                                contentDescription = null,
//                                tint = SimbanyuTheme.colors.brand,
//                                modifier = Modifier
//                                    .background(
//                                        color = SimbanyuTheme.colors.brand.copy(alpha = 0.3f),
//                                        shape = CircleShape
//                                    )
//                                    .padding(SimbanyuTheme.padding.medium)
//                            )
//                            Column(
//                                modifier = Modifier
//                                    .weight(1f)
//                            ) {
//                                SimbanyuText(
//                                    text = if (state.paymentMethodId.isEmpty()) {
//                                        "Pilih metode pembayaran"
//                                    } else {
//                                        state.paymentMethodName
//                                    },
//                                    style = SimbanyuTheme.typography.bodyProminent
//                                )
//                                if (!state.paymentMethodId.isEmpty()) {
//                                    Spacer(modifier = Modifier.height(SimbanyuTheme.spacing.small))
//                                    SimbanyuText(
//                                        text = "Biaya layanan: Rp. 5000",
//                                        style = SimbanyuTheme.typography.bodySmall
//                                    )
//                                }
//                            }
//                            Icon(
//                                painter = painterResource(R.drawable.ic_chevron_forward),
//                                contentDescription = null,
//                                tint = SimbanyuTheme.colors.brand,
//                            )
//                        }
//                    }
//                }
//            }
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun InvoicePreview() {
    InvoiceContent(
        state = fakeInvoiceState,
        onAction = {}
    )
}