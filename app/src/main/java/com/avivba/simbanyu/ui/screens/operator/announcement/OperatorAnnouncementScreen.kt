package com.avivba.simbanyu.ui.screens.operator.announcement

import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.text.input.TextFieldLineLimits
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.Scaffold
import androidx.compose.material3.pulltorefresh.PullToRefreshBox
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.input.KeyboardCapitalization
import androidx.compose.ui.tooling.preview.Devices
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.util.fastAny
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.avivba.simbanyu.R
import com.avivba.simbanyu.ui.components.buttons.SimbanyuButton
import com.avivba.simbanyu.ui.components.emptystate.EmptyState
import com.avivba.simbanyu.ui.components.item.AnnouncementItem
import com.avivba.simbanyu.ui.components.loadingbox.LoadingBox
import com.avivba.simbanyu.ui.components.text.SimbanyuText
import com.avivba.simbanyu.ui.components.textfield.SimbanyuTextField
import com.avivba.simbanyu.ui.components.topbar.TopBar
import com.avivba.simbanyu.ui.shared.Screen
import com.avivba.simbanyu.ui.theme.SimbanyuTheme
import com.avivba.simbanyu.ui.utils.ObserveAsEvent
import com.avivba.simbanyu.ui.utils.conditional
import org.koin.compose.viewmodel.koinViewModel


@Composable
fun OperatorAnnouncementScreen(
    modifier: Modifier = Modifier,
    viewModel: OperatorAnnouncementViewModel = koinViewModel(),
    onNavigate: (Screen) -> Unit = {},
    onNavigateBack: () -> Unit = {}
) {
    val state by viewModel.state.collectAsStateWithLifecycle()
    val context = LocalContext.current

    ObserveAsEvent(viewModel.event) {
        when (it) {
            OperatorAnnouncementEvent.NavigateBack -> {
                onNavigateBack()
            }

            OperatorAnnouncementEvent.AnnouncementCreated -> {
                Toast.makeText(
                    context,
                    "Pemberitahuan berhasil dibuat",
                    Toast.LENGTH_SHORT
                ).show()
            }
        }
    }

    OperatorAnnouncementContent(
        modifier = modifier,
        state = state,
        onNavigate = onNavigate,
        onAction = viewModel::onAction
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun OperatorAnnouncementContent(
    modifier: Modifier = Modifier,
    state: OperatorAnnouncementUiState,
    onAction: (OperatorAnnouncementAction) -> Unit = {},
    onNavigate: (Screen) -> Unit = {}
) {
    val listState = rememberLazyListState()
    val bottomSheetState = rememberModalBottomSheetState()

    Scaffold(
        modifier = modifier,
        containerColor = Color.Transparent,
        topBar = {
            TopBar(
                text = "Simbanyu Operator",
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
                                onAction(OperatorAnnouncementAction.NavigateBack)
                            }
                            .padding(
                                SimbanyuTheme.padding.large
                            )
                    )
                }
            )
        },
    ) { paddingValues ->
        LoadingBox(
            modifier = Modifier
                .padding(paddingValues),
            isLoading = state.isLoading
        ) {
            PullToRefreshBox(
                modifier = Modifier,
                isRefreshing = state.isRefreshing,
                onRefresh = {
                    onAction(OperatorAnnouncementAction.Refresh)
                }
            ) {
                Column(
                    modifier = modifier
                        .fillMaxSize(),
                    verticalArrangement = Arrangement.spacedBy(SimbanyuTheme.spacing.medium),
                ) {
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(top = SimbanyuTheme.padding.large)
                            .padding(horizontal = SimbanyuTheme.padding.large),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Column(
                            modifier = Modifier
                                .weight(1f)
                        ) {
                            SimbanyuText(
                                modifier = Modifier,
                                text = "Pemberitahuan",
                                style = SimbanyuTheme.typography.displayMedium
                            )

                            SimbanyuText(
                                text = "Sampaikan pemberitahuan kepada pelanggan",
                                style = SimbanyuTheme.typography.bodySmall,
                                color = SimbanyuTheme.colors.brand
                            )
                        }
                        SimbanyuButton(
                            text = "Tambah",
                            textColor = SimbanyuTheme.colors.actionBrand,
                            modifier = Modifier.border(
                                1.dp,
                                SimbanyuTheme.colors.actionBrand,
                                shape = SimbanyuTheme.shapes.extraLarge
                            ),
                            iconLeft = {
                                Icon(
                                    painter = painterResource(R.drawable.ic_add),
                                    contentDescription = null,
                                    tint = SimbanyuTheme.colors.actionBrand
                                )
                            }
                        ) {
                            onAction(OperatorAnnouncementAction.ShowCreateDialog)
                        }
                    }

                    if (state.announcements.isNotEmpty()) {
                        LazyColumn(
                            modifier = Modifier
                                .fillMaxSize(),
                            contentPadding = PaddingValues(SimbanyuTheme.padding.large),
                            state = listState,
                            verticalArrangement = Arrangement.spacedBy(
                                SimbanyuTheme.spacing.medium
                            )
                        ) {
                            items(
                                items = state.announcements,
                                key = { it.id }
                            ) { announcement ->
                                AnnouncementItem(
                                    title = announcement.title,
                                    content = announcement.body,
                                    time = announcement.date,
                                    iconId = R.drawable.ic_campaign_filled,
                                    canDelete = true,
                                    onDelete = {
                                        onAction(
                                            OperatorAnnouncementAction.DeleteAnnouncement(
                                                announcement
                                            )
                                        )
                                    }
                                )
                            }
                        }
                    } else {
                        EmptyState(
                            modifier = Modifier
                                .fillMaxSize()
                                .padding(SimbanyuTheme.padding.large),
                            message = "Belum ada pemberitahuan"
                        )
                    }
                }
            }

            if (state.isShowCreateDialog) {
                ModalBottomSheet(
                    sheetState = bottomSheetState,
                    containerColor = SimbanyuTheme.colors.surfaceCard,
                    onDismissRequest = {
                        onAction(OperatorAnnouncementAction.HideCreateDialog)
                    }
                ) {
                    Column(
                        modifier = Modifier
                            .verticalScroll(rememberScrollState())
                            .padding(
                                horizontal = SimbanyuTheme.padding.large
                            ),
                        verticalArrangement = Arrangement.spacedBy(
                            SimbanyuTheme.spacing.medium
                        )
                    ) {
                        SimbanyuText(
                            text = "Tambah Pemberitahuan",
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
                                painter = painterResource(R.drawable.ic_description),
                                contentDescription = null,
                                tint = SimbanyuTheme.colors.brand
                            )
                            SimbanyuText(
                                text = "JUDUL",
                                style = SimbanyuTheme.typography.labelOverline
                            )
                        }

                        SimbanyuTextField(
                            modifier = Modifier
                                .conditional(state.errorFields.fastAny { it == Fields.TITLE }) {
                                    border(
                                        width = 2.dp,
                                        color = SimbanyuTheme.colors.statusNegativeText,
                                        shape = SimbanyuTheme.shapes.large

                                    )
                                },
                            state = state.titleState,
                            hint = "Masukkan judul pemberitahuan...",
                            capitalization = KeyboardCapitalization.Words
                        )

                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                        ) {
                            Icon(
                                modifier = Modifier
                                    .height(16.dp)
                                    .padding(end = SimbanyuTheme.padding.medium),
                                painter = painterResource(R.drawable.ic_description),
                                contentDescription = null,
                                tint = SimbanyuTheme.colors.brand
                            )
                            SimbanyuText(
                                text = "ISI",
                                style = SimbanyuTheme.typography.labelOverline
                            )
                        }

                        SimbanyuTextField(
                            modifier = Modifier
                                .conditional(state.errorFields.fastAny { it == Fields.DESCRIPTION }) {
                                    border(
                                        width = 2.dp,
                                        color = SimbanyuTheme.colors.statusNegativeText,
                                        shape = SimbanyuTheme.shapes.large

                                    )
                                },
                            state = state.descriptionState,
                            lineLimits = TextFieldLineLimits.MultiLine(5, 5),
                            hint = "Masukkan isi pemberitahuan...",
                        )
                        Spacer(Modifier.height(SimbanyuTheme.spacing.medium))
                        SimbanyuButton(
                            modifier = Modifier
                                .fillMaxWidth()
                                .background(
                                    brush = SimbanyuTheme.colors.brush.brandVertical
                                ),
                            text = "Selesai",
                            iconRight = {
                                if (state.isCreatingAnnouncement) {
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
                            onAction(OperatorAnnouncementAction.CreateAnnouncement)
                        }
                        Spacer(Modifier.height(SimbanyuTheme.spacing.large))
                    }
                }
            }
        }
    }
}

@Preview(showBackground = true, device = Devices.PIXEL_7, showSystemUi = false)
@Composable
private fun OperatorAnnouncementPreview() {
    OperatorAnnouncementContent(
        state = OperatorAnnouncementUiState(
            announcements = fakeAnnouncements,
            isRefreshing = false,
            isShowCreateDialog = false
        )
    )
}