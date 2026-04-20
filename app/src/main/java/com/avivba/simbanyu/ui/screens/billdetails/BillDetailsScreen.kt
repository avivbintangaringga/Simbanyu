package com.avivba.simbanyu.ui.screens.billdetails

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Devices
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.avivba.simbanyu.R
import com.avivba.simbanyu.ui.components.buttons.SimbanyuButton
import com.avivba.simbanyu.ui.components.text.SimbanyuText
import com.avivba.simbanyu.ui.components.topbar.TopBar
import com.avivba.simbanyu.ui.screens.invoice.InvoiceAction
import com.avivba.simbanyu.ui.screens.paymentmethod.PaymentMethodUiAction
import com.avivba.simbanyu.ui.theme.SimbanyuTheme
import com.avivba.simbanyu.ui.utils.ObserveAsEvent
import com.avivba.simbanyu.ui.utils.leftBorder
import org.koin.compose.viewmodel.koinViewModel

@Composable
fun BillDetailsScreen(
    modifier: Modifier = Modifier,
    viewModel: BillDetailsViewModel = koinViewModel(),
    onNavigateBack: () -> Unit
) {
    val state by viewModel.state.collectAsStateWithLifecycle()

    ObserveAsEvent(viewModel.event) {
        when (it) {
            BillDetailsEvent.NavigateBack -> {
                onNavigateBack()
            }
        }
    }

    BillDetailsContent(
        modifier = modifier,
        state = state,
        onAction = viewModel::onAction
    )
}

@Composable
fun BillDetailsContent(
    modifier: Modifier = Modifier,
    state: BillDetailsUiState,
    onAction: (BillDetailsAction) -> Unit = {}
) {
    val scrollState = rememberScrollState()

    Scaffold(
        modifier = modifier,
        containerColor = Color.Transparent,
        topBar = {
            TopBar(
                text = "Detail tagihan",
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
                                onAction(BillDetailsAction.NavigateBack)
                            }
                            .padding(
                                SimbanyuTheme.padding.large
                            )
                    )
                }
            )
        }
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(paddingValues)
                .padding(
                    SimbanyuTheme.padding.large,
                )
                .verticalScroll(scrollState),
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

            Spacer(Modifier.height(24.dp))

            Column(
                modifier = Modifier
                    .fillMaxWidth(),
                verticalArrangement = Arrangement.spacedBy(
                    SimbanyuTheme.spacing.medium
                ),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                SimbanyuText(
                    text = "STATUS PEMBAYARAN",
                    style = SimbanyuTheme.typography.labelOverline,
                )

                Row(
                    modifier = Modifier
                        .background(
                            color = SimbanyuTheme.colors.statusPositive,
                            shape = SimbanyuTheme.shapes.extraLarge
                        )
                        .padding(
                            vertical = SimbanyuTheme.padding.medium,
                            horizontal = SimbanyuTheme.padding.large
                        ),
                    verticalAlignment = Alignment.CenterVertically,
                ) {
                    Icon(
                        painter = painterResource(R.drawable.ic_check_circle_filled),
                        contentDescription = null,
                        tint = SimbanyuTheme.colors.statusPositiveText
                    )

                    SimbanyuText(
                        modifier = Modifier.padding(start = SimbanyuTheme.padding.medium),
                        text = "LUNAS",
                        style = SimbanyuTheme.typography.titleSection.copy(
                            fontWeight = FontWeight(900),
                            letterSpacing = 1.3.sp
                        ),
                        color = SimbanyuTheme.colors.statusPositiveText,
                    )
                }

                SimbanyuText(
                    text = "Tagihan Bulan ${state.period}",
                    style = SimbanyuTheme.typography.labelOverline,
                )

                Spacer(Modifier.height(32.dp))

                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .background(
                            color = SimbanyuTheme.colors.surfaceCardAlternate,
                            shape = SimbanyuTheme.shapes.large
                        )
                        .padding(
                            SimbanyuTheme.padding.large
                        ),
                    verticalArrangement = Arrangement.spacedBy(
                        SimbanyuTheme.spacing.small
                    )
                ) {
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(
                                bottom = SimbanyuTheme.padding.medium
                            ),
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.spacedBy(
                            SimbanyuTheme.spacing.medium
                        )
                    ) {
                        Icon(
                            painter = painterResource(R.drawable.ic_contract),
                            contentDescription = null,
                            tint = SimbanyuTheme.colors.brand,
                            modifier = Modifier.size(16.dp)
                        )

                        SimbanyuText(
                            text = "Rincian Biaya",
                            style = SimbanyuTheme.typography.bodyProminent,
                            modifier = Modifier.weight(1f)
                        )
                    }

                    state.costs.forEach { detail ->
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
                            text = state.totalAmount,
                            style = SimbanyuTheme.typography.bodyProminent,
                            color = SimbanyuTheme.colors.brand
                        )
                    }
                }

                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(
                            top = SimbanyuTheme.padding.large
                        )
                        .background(
                            color = SimbanyuTheme.colors.surfaceCardAlternate,
                            shape = SimbanyuTheme.shapes.large
                        )
                        .padding(
                            SimbanyuTheme.padding.large
                        ),
                    verticalArrangement = Arrangement.spacedBy(
                        SimbanyuTheme.spacing.small
                    )
                ) {
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(
                                bottom = SimbanyuTheme.padding.medium
                            ),
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.spacedBy(
                            SimbanyuTheme.spacing.medium
                        )
                    ) {
                        Icon(
                            painter = painterResource(R.drawable.ic_info_filled),
                            contentDescription = null,
                            tint = SimbanyuTheme.colors.brand,
                            modifier = Modifier.size(16.dp)
                        )

                        SimbanyuText(
                            text = "Informasi Transaksi",
                            style = SimbanyuTheme.typography.bodyProminent,
                            modifier = Modifier.weight(1f)
                        )
                    }

                    SimbanyuText(
                        text = "METODE PEMBAYARAN",
                        style = SimbanyuTheme.typography.bodySmall,
                        color = SimbanyuTheme.colors.statusNeutral
                    )

                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(bottom = SimbanyuTheme.padding.medium),
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.spacedBy(
                            SimbanyuTheme.spacing.medium
                        )
                    ) {
                        Icon(
                            painter = painterResource(R.drawable.ic_payment),
                            contentDescription = null,
                            tint = SimbanyuTheme.colors.brand,
                            modifier = Modifier
                                .height(20.dp)
                                .background(
                                    color = SimbanyuTheme.colors.brand.copy(alpha = 0.3f),
                                    shape = SimbanyuTheme.shapes.small
                                )
                                .padding(
                                    vertical = SimbanyuTheme.padding.extraSmall,
                                    horizontal = SimbanyuTheme.padding.large
                                )
                        )

                        SimbanyuText(
                            text = state.paymentMethodName,
                            style = SimbanyuTheme.typography.bodySmall,
                        )
                    }

                    SimbanyuText(
                        text = "WAKTU TRANSAKSI",
                        style = SimbanyuTheme.typography.bodySmall,
                        color = SimbanyuTheme.colors.statusNeutral
                    )

                    SimbanyuText(
                        text = state.transactionDate,
                        style = SimbanyuTheme.typography.bodySmall,
                    )

                    SimbanyuText(
                        modifier = Modifier.padding(
                            top = SimbanyuTheme.padding.medium
                        ),
                        text = "NOMOR REFERENSI",
                        style = SimbanyuTheme.typography.bodySmall,
                        color = SimbanyuTheme.colors.statusNeutral
                    )

                    SimbanyuText(
                        text = state.referenceNumber,
                        style = SimbanyuTheme.typography.bodySmall,
                    )
                }


                SimbanyuButton(
                    modifier = Modifier
                        .padding(
                            vertical = SimbanyuTheme.padding.large
                        )
                        .fillMaxWidth()
                        .background(
                            brush = SimbanyuTheme.colors.brush.brandVertical,
                            shape = SimbanyuTheme.shapes.extraLarge
                        ),
                    text = "Unduh Bukti Pembayaran",
                    onClick = {
                        onAction(BillDetailsAction.DownloadReceipt)
                    },
                    iconLeft = {
                        Icon(
                            painter = painterResource(R.drawable.ic_download),
                            contentDescription = null,
                            tint = SimbanyuTheme.colors.brandText
                        )
                    }
                )
            }
        }
    }
}

@Preview(showBackground = true, device = Devices.PIXEL_7, showSystemUi = false)
@Composable
fun BillDetailsPreview() {
    BillDetailsContent(
        state = fakeBillDetailsUiState
    )
}