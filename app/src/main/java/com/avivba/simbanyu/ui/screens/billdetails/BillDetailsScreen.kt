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
import com.avivba.simbanyu.ui.theme.SimbanyuTheme
import com.avivba.simbanyu.ui.utils.ObserveAsEvent
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

            is BillDetailsEvent.OpenPaymentPage -> {
                val url = it.url
                // TODO: Handle open payment URL
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

                var iconId: Int
                var iconTint: Color
                var backgroundColor: Color

                when (state.status) {
                    BillingStatus.PAID -> {
                        iconId = R.drawable.ic_check_circle_filled
                        iconTint = SimbanyuTheme.colors.statusPositiveText
                        backgroundColor = SimbanyuTheme.colors.statusPositive
                    }

                    BillingStatus.PENDING -> {
                        iconId = R.drawable.ic_pending
                        iconTint = SimbanyuTheme.colors.statusWarningText
                        backgroundColor = SimbanyuTheme.colors.statusWarning
                    }

                    BillingStatus.UNPAID -> {
                        iconId = R.drawable.ic_error
                        iconTint = SimbanyuTheme.colors.statusNegativeText
                        backgroundColor = SimbanyuTheme.colors.statusNegative
                    }
                }

                Row(
                    modifier = Modifier
                        .background(
                            color = backgroundColor,
                            shape = SimbanyuTheme.shapes.extraLarge
                        )
                        .padding(
                            vertical = SimbanyuTheme.padding.medium,
                            horizontal = SimbanyuTheme.padding.large
                        ),
                    verticalAlignment = Alignment.CenterVertically,
                ) {
                    Icon(
                        painter = painterResource(iconId),
                        contentDescription = null,
                        tint = iconTint,
                    )

                    SimbanyuText(
                        modifier = Modifier.padding(start = SimbanyuTheme.padding.medium),
                        text = state.status.status,
                        style = SimbanyuTheme.typography.titleSection.copy(
                            fontWeight = FontWeight(900),
                            letterSpacing = 1.3.sp
                        ),
                        color = iconTint
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

                    state.details.forEach { detail ->
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


                if (state.status == BillingStatus.PAID) {
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
                } else if (state.status == BillingStatus.PENDING) {
                    SimbanyuButton(
                        modifier = Modifier
                            .padding(
                                top = SimbanyuTheme.padding.large
                            )
                            .fillMaxWidth()
                            .background(
                                color = SimbanyuTheme.colors.brand.copy(alpha = 0.3f),
                                shape = SimbanyuTheme.shapes.extraLarge
                            ),
                        text = "Periksa Pembayaran",
                        textColor = SimbanyuTheme.colors.brand,
                        onClick = {
                            onAction(BillDetailsAction.OpenPaymentPage)
                        },
                        iconLeft = {
                            Icon(
                                painter = painterResource(R.drawable.ic_refresh),
                                contentDescription = null,
                                tint = SimbanyuTheme.colors.brand
                            )
                        }
                    )

                    SimbanyuButton(
                        modifier = Modifier
                            .padding(
                                bottom = SimbanyuTheme.padding.large,
                                top = SimbanyuTheme.padding.small
                            )
                            .fillMaxWidth()
                            .background(
                                brush = SimbanyuTheme.colors.brush.brandVertical,
                                shape = SimbanyuTheme.shapes.extraLarge
                            ),
                        text = "Buka Halaman Pembayaran",
                        onClick = {
                            onAction(BillDetailsAction.OpenPaymentPage)
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