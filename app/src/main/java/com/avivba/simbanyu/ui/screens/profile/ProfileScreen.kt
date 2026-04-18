package com.avivba.simbanyu.ui.screens.profile

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.text.input.TextFieldLineLimits
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Icon
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.dropShadow
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.shadow.Shadow
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Devices
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.avivba.simbanyu.R
import com.avivba.simbanyu.ui.components.buttons.SimbanyuButton
import com.avivba.simbanyu.ui.components.text.SimbanyuText
import com.avivba.simbanyu.ui.components.textfield.SimbanyuTextField
import com.avivba.simbanyu.ui.screens.main.Screen
import com.avivba.simbanyu.ui.screens.report.ReportAction
import com.avivba.simbanyu.ui.theme.SimbanyuTheme
import org.koin.compose.viewmodel.koinViewModel

@Composable
fun ProfileScreen(
    viewModel: ProfileViewModel = koinViewModel(),
    onNavigate: (Screen) -> Unit = {}
) {
    val state by viewModel.state.collectAsStateWithLifecycle()

    ProfileContent(
        state = state,
        onAction = viewModel::onAction
    )
}

@Composable
fun ProfileContent(
    modifier: Modifier = Modifier,
    state: ProfileUiState,
    onAction: (ProfileAction) -> Unit = {}
) {
    val scrollState = rememberScrollState()
    Column(
        modifier = modifier
            .fillMaxSize()
            .verticalScroll(scrollState)
            .padding(SimbanyuTheme.padding.large),
        verticalArrangement = Arrangement.spacedBy(
            SimbanyuTheme.spacing.medium
        ),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Spacer(modifier = Modifier.height(SimbanyuTheme.spacing.large))
        Box(
            modifier = Modifier
                .dropShadow(
                    shape = CircleShape,
                    shadow = Shadow(
                        radius = SimbanyuTheme.spacing.medium,
                        alpha = 0.3f
                    )
                )
                .clip(CircleShape)
                .background(SimbanyuTheme.colors.surfaceCardAlternate)

        ) {
            Box(
                modifier = Modifier
                    .padding(8.dp)
                    .clip(CircleShape)
                    .size(150.dp)
                    .background(
                        color = SimbanyuTheme.colors.brand
                    )
            ) {

            }
        }

        SimbanyuText(
            modifier = Modifier.padding(top = SimbanyuTheme.spacing.medium),
            text = state.name,
            style = SimbanyuTheme.typography.displayLarge
        )

        Spacer(modifier = Modifier.height(SimbanyuTheme.spacing.extraExtraLarge))

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
                    painter = painterResource(R.drawable.ic_speed),
                    contentDescription = null,
                    tint = SimbanyuTheme.colors.brand
                )
                SimbanyuText(
                    text = "NOMOR METER",
                    style = SimbanyuTheme.typography.labelOverline
                )
            }

            SimbanyuText(
                text = state.meterId,
                style = SimbanyuTheme.typography.titleSection
            )
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
                    painter = painterResource(R.drawable.ic_call),
                    contentDescription = null,
                    tint = SimbanyuTheme.colors.brand
                )
                SimbanyuText(
                    text = "NOMOR HP",
                    style = SimbanyuTheme.typography.labelOverline
                )
            }

            SimbanyuText(
                text = state.phone,
                style = SimbanyuTheme.typography.titleSection
            )
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
                    painter = painterResource(R.drawable.ic_location),
                    contentDescription = null,
                    tint = SimbanyuTheme.colors.brand
                )
                SimbanyuText(
                    text = "ALAMAT",
                    style = SimbanyuTheme.typography.labelOverline
                )
            }

            SimbanyuText(
                text = state.address,
                style = SimbanyuTheme.typography.titleSection
            )
        }

        Spacer(modifier = Modifier.height(SimbanyuTheme.spacing.extraLarge))

        SimbanyuButton(
            modifier = Modifier
                .fillMaxWidth()
                .background(
                    color = SimbanyuTheme.colors.statusNegativeText
                ),
            text = "Logout",
            iconRight = {
                Icon(
                    painter = painterResource(R.drawable.ic_logout),
                    contentDescription = null,
                    tint = SimbanyuTheme.colors.brandText
                )
            },
            onClick = {
                onAction(ProfileAction.Logout)
            }
        )
    }
}

@Preview(showBackground = true, device = Devices.PIXEL_7, showSystemUi = false)
@Composable
private fun ProfilePreview() {
    ProfileContent(
        state = ProfileUiState(
            name = "John Doe",
            meterId = "DX920130",
            phone = "08123412312",
            address = "Jl. Raya No. 123",

            )
    )
}