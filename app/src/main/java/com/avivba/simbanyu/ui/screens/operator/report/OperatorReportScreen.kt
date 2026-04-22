package com.avivba.simbanyu.ui.screens.operator.report

import android.widget.Toast
import androidx.compose.animation.animateContentSize
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.requiredSize
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.text.input.TextFieldLineLimits
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.ModalBottomSheetProperties
import androidx.compose.material3.pulltorefresh.PullToRefreshBox
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.DpOffset
import androidx.compose.ui.unit.dp
import androidx.compose.ui.util.fastAny
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.avivba.simbanyu.R
import com.avivba.simbanyu.ui.components.buttons.SimbanyuButton
import com.avivba.simbanyu.ui.components.item.ReportItem
import com.avivba.simbanyu.ui.components.text.SimbanyuText
import com.avivba.simbanyu.ui.components.textfield.SimbanyuTextField
import com.avivba.simbanyu.ui.screens.billhistory.BillHistoryAction
import com.avivba.simbanyu.ui.screens.operator.announcement.Fields
import com.avivba.simbanyu.ui.screens.operator.announcement.OperatorAnnouncementAction
import com.avivba.simbanyu.ui.screens.report.ReportAction
import com.avivba.simbanyu.ui.screens.report.fakeReports
import com.avivba.simbanyu.ui.shared.Screen
import com.avivba.simbanyu.ui.shared.model.ReportStatus
import com.avivba.simbanyu.ui.theme.SimbanyuTheme
import com.avivba.simbanyu.ui.utils.ObserveAsEvent
import com.avivba.simbanyu.ui.utils.conditional
import com.avivba.simbanyu.ui.utils.dashedBorder
import org.koin.compose.viewmodel.koinViewModel

@Composable
fun OperatorReportScreen(
    modifier: Modifier = Modifier,
    viewModel: OperatorReportViewModel = koinViewModel(),
    onNavigate: (Screen) -> Unit = {},
) {
    val state by viewModel.state.collectAsStateWithLifecycle()
    val context = LocalContext.current

    ObserveAsEvent(viewModel.event) {
        when (it) {
            OperatorReportEvent.OnSent -> {
                Toast.makeText(
                    context,
                    "Laporan berhasil diperbarui",
                    Toast.LENGTH_SHORT
                ).show()
            }
        }
    }

    OperatorReportContent(
        modifier = modifier,
        state = state,
        onAction = viewModel::onAction
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun OperatorReportContent(
    modifier: Modifier = Modifier,
    state: OperatorReportUiState,
    onAction: (OperatorReportAction) -> Unit = {}
) {
    val listState = rememberLazyListState()
    val bottomSheetState = rememberModalBottomSheetState(
        skipPartiallyExpanded = true
    )

    PullToRefreshBox(
        modifier = modifier
            .fillMaxSize()
            .padding(
                SimbanyuTheme.padding.large
            ),
        isRefreshing = state.isRefreshing,
        onRefresh = {
            onAction(OperatorReportAction.Refresh)
        }
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize(),
            verticalArrangement = Arrangement.spacedBy(
                SimbanyuTheme.spacing.medium
            )
        ) {
            SimbanyuText(
                text = "Laporan Pelanggan",
                style = SimbanyuTheme.typography.displayMedium
            )

            SimbanyuText(
                text = "Aduan dari pelanggan terkait layanan air",
                style = SimbanyuTheme.typography.bodySmall,
                color = SimbanyuTheme.colors.brand
            )

            LazyColumn(
                modifier = modifier
                    .fillMaxSize(),
                verticalArrangement = Arrangement.spacedBy(
                    SimbanyuTheme.spacing.medium
                ),
                state = listState
            ) {
                items(
                    items = state.reports,
                    key = { it.report.id }
                ) { report ->
                    Column(
                        modifier = Modifier
                            .animateContentSize()
                    ) {
                        ReportItem(
                            modifier = Modifier,
                            report = report.report,
                            showHandleReport = true,
                            showUser = true,
                            onClickExpand = {
                                onAction(OperatorReportAction.ToggleItemExpand(report))
                            },
                            onClickHandleReport = {
                                onAction(OperatorReportAction.UpdateReport(report))
                            }
                        )
                    }
                }
            }


            if (state.currentReport != null) {
                ModalBottomSheet(
                    sheetState = bottomSheetState,
                    onDismissRequest = {
                        if (!state.currentReport.isSending) {
                            onAction(OperatorReportAction.HideUpdateReport)
                        }
                    },
                    properties = ModalBottomSheetProperties(
                        shouldDismissOnBackPress = !state.currentReport.isSending,
                        shouldDismissOnClickOutside = !state.currentReport.isSending,

                        )
                ) {
                    Column(
                        modifier = Modifier
                            .padding(
                                horizontal = SimbanyuTheme.padding.large
                            ),
                        verticalArrangement = Arrangement.spacedBy(
                            SimbanyuTheme.spacing.medium
                        )
                    ) {
                        var isExpanded by remember { mutableStateOf(false) }

                        SimbanyuText(
                            text = "Update Laporan",
                            style = SimbanyuTheme.typography.displayMedium
                        )

                        Spacer(Modifier.height(SimbanyuTheme.spacing.medium))

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
                                text = "STATUS",
                                style = SimbanyuTheme.typography.labelOverline
                            )
                        }

                        Column(
                            modifier = Modifier
                                .fillMaxWidth()
                        ) {
                            Row(
                                modifier = Modifier
                                    .background(
                                        color = SimbanyuTheme.colors.surfaceCardAlternate,
                                        shape = SimbanyuTheme.shapes.medium
                                    )
                                    .clickable {
                                        isExpanded = true
                                    }
                                    .padding(SimbanyuTheme.padding.medium),
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                SimbanyuText(
                                    modifier = Modifier.padding(end = SimbanyuTheme.padding.small),
                                    text = state.currentReport.report.status.status,
                                    style = SimbanyuTheme.typography.bodyProminent
                                )
                                Icon(
                                    painter = painterResource(if (isExpanded) R.drawable.ic_arrow_dropdown_up else R.drawable.ic_arrow_dropdown_down),
                                    contentDescription = null,
                                )
                            }

                            DropdownMenu(
                                modifier = Modifier,
                                expanded = isExpanded,
                                onDismissRequest = {
                                    isExpanded = false
                                },
                                containerColor = SimbanyuTheme.colors.surfaceCard,
                            ) {
                                ReportStatus.entries.forEach { status ->
                                    DropdownMenuItem(
                                        text = {
                                            SimbanyuText(
                                                text = status.status,
                                                style = SimbanyuTheme.typography.bodyStandard
                                            )
                                        },
                                        onClick = {
                                            // TODO: Handle on status selected
                                            isExpanded = false
                                        }
                                    )
                                }
                            }
                        }

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
                                text = "CATATAN PETUGAS",
                                style = SimbanyuTheme.typography.labelOverline
                            )
                        }

                        SimbanyuTextField(
                            modifier = Modifier,
                            state = state.notesState,
                            lineLimits = TextFieldLineLimits.MultiLine(3, 3),
                            hint = "Masukkan catatan petugas jika diperlukan...",
                        )

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
                                text = "BUKTI PENANGANAN",
                                style = SimbanyuTheme.typography.labelOverline
                            )
                        }

                        Column(
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(150.dp)
                                .clip(SimbanyuTheme.shapes.large)
                                .clickable {
                                    onAction(OperatorReportAction.PickImage)
                                }
                                .conditional(
                                    state.imageUri.isEmpty()
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

                        Spacer(Modifier.height(SimbanyuTheme.spacing.medium))

                        SimbanyuButton(
                            modifier = Modifier
                                .fillMaxWidth()
                                .background(
                                    brush = SimbanyuTheme.colors.brush.brandVertical
                                ),
                            text = "Selesai",
                            iconRight = {
                                if (state.currentReport.isSending) {
                                    CircularProgressIndicator(
                                        modifier = Modifier.size(18.dp),
                                        strokeWidth = 2.dp,
                                        color = SimbanyuTheme.colors.actionBrandText
                                    )
                                } else {
                                    Icon(
                                        modifier = Modifier.size(18.dp),
                                        painter = painterResource(R.drawable.ic_check),
                                        contentDescription = null,
                                        tint = SimbanyuTheme.colors.actionBrandText
                                    )
                                }
                            },
                        ) {
                            onAction(OperatorReportAction.SendUpdatedReport)
                        }
                        Spacer(Modifier.height(SimbanyuTheme.spacing.large))
                    }
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun OperatorReportScreenPreview() {
    SimbanyuTheme {
        OperatorReportContent(
            state = OperatorReportUiState(
                reports = fakeReports.map { report ->
                    OperatorReport(
                        report = report,
                        false
                    )
                }
            ),
        )
    }
}
