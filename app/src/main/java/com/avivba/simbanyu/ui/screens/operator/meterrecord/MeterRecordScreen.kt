package com.avivba.simbanyu.ui.screens.operator.meterrecord

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.requiredSize
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.text.input.TextFieldLineLimits
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.CircularProgressIndicator
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
import androidx.compose.ui.unit.sp
import androidx.compose.ui.util.fastAny
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.avivba.simbanyu.R
import com.avivba.simbanyu.ui.components.buttons.SimbanyuButton
import com.avivba.simbanyu.ui.components.text.SimbanyuText
import com.avivba.simbanyu.ui.components.textfield.SimbanyuTextField
import com.avivba.simbanyu.ui.components.topbar.TopBar
import com.avivba.simbanyu.ui.screens.report.ReportAction
import com.avivba.simbanyu.ui.shared.Screen
import com.avivba.simbanyu.ui.theme.SimbanyuTheme
import com.avivba.simbanyu.ui.utils.ObserveAsEvent
import com.avivba.simbanyu.ui.utils.conditional
import com.avivba.simbanyu.ui.utils.dashedBorder
import org.koin.compose.viewmodel.koinViewModel

@Composable
fun MeterRecordScreen(
    modifier: Modifier = Modifier,
    viewModel: MeterRecordViewModel = koinViewModel(),
    onNavigate: (Screen) -> Unit = {},
    onNavigateBack: () -> Unit = {}
) {
    val state by viewModel.state.collectAsStateWithLifecycle()

    ObserveAsEvent(viewModel.event) {
        when (it) {
            MeterRecordEvents.NavigateBack -> {
                onNavigateBack()
            }

            MeterRecordEvents.PickImage -> {
                // TODO: Implement pick image
            }

            MeterRecordEvents.RecordSent -> {
                // TODO: Implement record sent
            }
        }
    }

    MeterRecordContent(
        modifier = modifier,
        state = state,
        onAction = viewModel::onAction,
    )
}

@Composable
fun MeterRecordContent(
    modifier: Modifier = Modifier,
    state: MeterRecordUiState,
    onAction: (MeterRecordAction) -> Unit = {},
) {
    val scrollState = rememberScrollState()

    Scaffold(
        modifier = modifier,
        containerColor = Color.Transparent,
        topBar = {
            TopBar(
                text = "Catat Meter",
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
                                onAction(MeterRecordAction.NavigateBack)
                            }
                            .padding(
                                SimbanyuTheme.padding.large
                            )
                    )
                }
            )
        },
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .verticalScroll(scrollState)
                .padding(paddingValues)
                .padding(
                    SimbanyuTheme.padding.large
                ),
            verticalArrangement = Arrangement.spacedBy(
                SimbanyuTheme.spacing.medium
            )
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth(),
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
                    modifier = Modifier
                        .fillMaxWidth(),
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

            Spacer(Modifier.height(SimbanyuTheme.spacing.small))

            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(
                        color = SimbanyuTheme.colors.surfaceCardAlternate,
                        shape = SimbanyuTheme.shapes.large
                    )
                    .padding(
                        SimbanyuTheme.padding.large
                    ),
                horizontalArrangement = Arrangement.spacedBy(
                    SimbanyuTheme.spacing.medium
                )
            ) {
                Column(
                    modifier = Modifier
                        .weight(1f),
                    verticalArrangement = Arrangement.spacedBy(
                        SimbanyuTheme.spacing.small
                    )
                ) {
                    SimbanyuText(
                        text = "TANGGAL TERAKHIR",
                        style = SimbanyuTheme.typography.labelOverline,
                    )

                    SimbanyuText(
                        text = state.lastMeterRecordDate,
                        style = SimbanyuTheme.typography.bodySmall,
                    )
                }

                Column(
                    modifier = Modifier
                        .weight(1f),
                    verticalArrangement = Arrangement.spacedBy(
                        SimbanyuTheme.spacing.small
                    )
                ) {
                    SimbanyuText(
                        text = "METER TERAKHIR",
                        style = SimbanyuTheme.typography.labelOverline,
                    )

                    SimbanyuText(
                        text = "${state.lastMeterValue} m³",
                        style = SimbanyuTheme.typography.bodySmall,
                    )
                }
            }

            Column(
                modifier = Modifier
                    .fillMaxWidth(),
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .background(
                            color = SimbanyuTheme.colors.surfaceCardAlternate,
                            shape = SimbanyuTheme.shapes.large
                        )
                        .padding(SimbanyuTheme.padding.large),
                    verticalArrangement = Arrangement.spacedBy(SimbanyuTheme.spacing.medium)
                ) {
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                    ) {
                        Icon(
                            modifier = Modifier
                                .height(16.dp)
                                .padding(end = SimbanyuTheme.padding.medium),
                            painter = painterResource(R.drawable.ic_speed_filled),
                            contentDescription = null,
                            tint = SimbanyuTheme.colors.brand
                        )
                        SimbanyuText(
                            text = "ANGKA METER SAAT INI",
                            style = SimbanyuTheme.typography.labelOverline
                        )
                    }

                    SimbanyuTextField(
                        modifier = Modifier
                            .conditional(state.errorFields.fastAny { it == Fields.METER_RECORD }) {
                                border(
                                    width = 2.dp,
                                    color = SimbanyuTheme.colors.statusNegativeText,
                                    shape = SimbanyuTheme.shapes.large

                                )
                            },
                        state = state.meterRecordState,
                        hint = "${state.lastMeterValue + 1}",
                        fontSize = 28.sp,
                        suffix = {
                            SimbanyuText(
                                text = "m³",
                                style = SimbanyuTheme.typography.labelUnit,
                                color = SimbanyuTheme.colors.brand,
                                fontSize = 24.sp
                            )
                        }
                    )

                    Row(
                        modifier = Modifier
                            .padding(SimbanyuTheme.padding.small),
                        horizontalArrangement = Arrangement.spacedBy(
                            SimbanyuTheme.spacing.medium
                        ),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Icon(
                            modifier = Modifier.height(16.dp),
                            painter = painterResource(R.drawable.ic_info_filled),
                            contentDescription = null,
                            tint = SimbanyuTheme.colors.statusWarningText
                        )

                        SimbanyuText(
                            text = "Pastikan angka lebih besar dari catatan sebelumnya",
                            style = SimbanyuTheme.typography.bodySmall,
                            color = SimbanyuTheme.colors.brand
                        )
                    }
                }
            }


            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(
                        color = SimbanyuTheme.colors.surfaceCardAlternate,
                        shape = SimbanyuTheme.shapes.large
                    )
                    .padding(SimbanyuTheme.padding.large),
                verticalArrangement = Arrangement.spacedBy(SimbanyuTheme.spacing.medium)
            ) {
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                ) {
                    Icon(
                        modifier = Modifier
                            .height(16.dp)
                            .padding(end = SimbanyuTheme.padding.medium),
                        painter = painterResource(R.drawable.ic_photo_camera_filled),
                        contentDescription = null,
                        tint = SimbanyuTheme.colors.brand
                    )
                    SimbanyuText(
                        text = "BUKTI FOTO",
                        style = SimbanyuTheme.typography.labelOverline
                    )
                }

                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(150.dp)
                        .clip(SimbanyuTheme.shapes.large)
                        .clickable {
                            onAction(MeterRecordAction.PickImage)
                        }
                        .conditional(
                            state.errorFields.fastAny {
                                it == Fields.IMAGE
                            }
                        ) {
                            dashedBorder(
                                strokeWidth = 4.dp,
                                gapLength = 6.dp,
                                dashLength = 8.dp,
                                color = SimbanyuTheme.colors.statusNegativeText,
                                shape = SimbanyuTheme.shapes.large
                            )
                        }
                        .dashedBorder(
                            strokeWidth = 4.dp,
                            gapLength = 6.dp,
                            dashLength = 8.dp,
                            color = SimbanyuTheme.colors.borderCardAlternate,
                            shape = SimbanyuTheme.shapes.large
                        ),
                    verticalArrangement = Arrangement.spacedBy(
                        SimbanyuTheme.spacing.medium,
                        alignment = Alignment.CenterVertically
                    ),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Icon(
                        modifier = Modifier
                            .requiredSize(40.dp),
                        painter = painterResource(R.drawable.ic_photo_camera_filled),
                        contentDescription = null,
                        tint = SimbanyuTheme.colors.statusNeutral
                    )

                    SimbanyuText(
                        text = "Unggah foto (maks 5MB)",
                        style = SimbanyuTheme.typography.bodySmall,
                        color = SimbanyuTheme.colors.statusNeutral
                    )
                }
            }

            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(
                        color = SimbanyuTheme.colors.surfaceCardAlternate,
                        shape = SimbanyuTheme.shapes.large
                    )
                    .padding(SimbanyuTheme.padding.large),
                verticalArrangement = Arrangement.spacedBy(SimbanyuTheme.spacing.medium)
            ) {
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                ) {
                    Icon(
                        modifier = Modifier
                            .height(16.dp)
                            .padding(end = SimbanyuTheme.padding.medium),
                        painter = painterResource(R.drawable.ic_description_filled),
                        contentDescription = null,
                        tint = SimbanyuTheme.colors.brand
                    )
                    SimbanyuText(
                        text = "CATATAN PETUGAS (OPSIONAL)",
                        style = SimbanyuTheme.typography.labelOverline
                    )
                }

                SimbanyuTextField(
                    modifier = Modifier,
                    state = state.notesState,
                    lineLimits = TextFieldLineLimits.MultiLine(6, 8),
                    hint = "Catatan petugas apabila diperlukan...",
                )
            }

            SimbanyuButton(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(
                        brush = SimbanyuTheme.colors.brush.brandVertical
                    ),
                text = "Kirim Laporan",
                iconRight = {
                    if (state.isSendingRecord) {
                        CircularProgressIndicator(
                            modifier = Modifier.size(18.dp),
                            strokeWidth = 2.dp,
                            color = SimbanyuTheme.colors.actionBrandText
                        )
                    } else {
                        Icon(
                            painter = painterResource(R.drawable.ic_send),
                            contentDescription = null,
                            tint = SimbanyuTheme.colors.brandText
                        )
                    }
                },
                onClick = {
                    onAction(MeterRecordAction.SendRecord)
                }
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun MeterRecordContentPreview() {
    MeterRecordContent(
        state = MeterRecordUiState(
            name = "Budi Santoso",
            address = "Jl. Merdeka No. 10, Jakarta",
            meterId = "MTR001",
            category = "Rumah Tangga A",
        )
    )
}