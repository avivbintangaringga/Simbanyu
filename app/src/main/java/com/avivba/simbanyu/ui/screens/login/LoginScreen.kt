package com.avivba.simbanyu.ui.screens.login

import android.widget.Toast
import androidx.compose.animation.animateContentSize
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.CircularProgressIndicator
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
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.avivba.simbanyu.R
import com.avivba.simbanyu.ui.components.buttons.SimbanyuButton
import com.avivba.simbanyu.ui.components.text.SimbanyuText
import com.avivba.simbanyu.ui.components.textfield.SimbanyuTextField
import com.avivba.simbanyu.ui.shared.Screen
import com.avivba.simbanyu.ui.theme.SimbanyuTheme
import com.avivba.simbanyu.ui.utils.ObserveAsEvent
import com.avivba.simbanyu.ui.utils.leftBorder
import org.koin.compose.viewmodel.koinViewModel


@Composable
fun LoginScreen(
    modifier: Modifier = Modifier,
    viewModel: LoginViewModel = koinViewModel(),
    onNavigate: (Screen) -> Unit = {}
) {
    val state by viewModel.state.collectAsStateWithLifecycle()
    val context = LocalContext.current

    ObserveAsEvent(viewModel.event) {
        when (it) {
            is LoginEvent.GoToSubscriberDashboard -> {
                onNavigate(Screen.Main)
            }

            is LoginEvent.GoToOperatorDashboard -> {
                onNavigate(Screen.OperatorMain)
            }

            is LoginEvent.ShowToast -> {
                Toast.makeText(context, it.message, Toast.LENGTH_SHORT).show()
            }
        }
    }

    LoginContent(
        modifier = modifier,
        state = state,
        onAction = viewModel::onAction,
    )
}

@Composable
fun LoginContent(
    modifier: Modifier = Modifier,
    state: LoginUiState,
    onAction: (LoginAction) -> Unit = {},
) {
    val scrollState = rememberScrollState()

    fun tryLogin() {
        if (!state.isLoading) {
            onAction(LoginAction.Login)
        }
    }

    Scaffold(
        modifier = modifier,
        containerColor = Color.Transparent
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .verticalScroll(scrollState)
                .padding(SimbanyuTheme.padding.extraLarge),
            verticalArrangement = Arrangement.SpaceEvenly,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Column(
                modifier = Modifier.padding(bottom = SimbanyuTheme.padding.large),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.spacedBy(SimbanyuTheme.spacing.medium)
            ) {
                Image(
                    painter = painterResource(R.drawable.logo),
                    contentDescription = "Logo",
                    modifier = Modifier.height(64.dp)
                )
                SimbanyuText(
                    text = "SIMBANYU",
                    style = SimbanyuTheme.typography.displayHero,
                    fontWeight = FontWeight.Bold,
                    color = SimbanyuTheme.colors.brand,
                )
                SimbanyuText(
                    text = "PAM Dusun Sawahan 5"
                )
            }
            Column(
                modifier = Modifier
                    .dropShadow(
                        shape = SimbanyuTheme.shapes.extraLarge,
                        shadow = Shadow(
                            radius = SimbanyuTheme.spacing.extraLarge,
                            color = SimbanyuTheme.colors.shadow,
                            alpha = 0.2f
                        )
                    )
                    .animateContentSize()
                    .background(
                        color = SimbanyuTheme.colors.surfaceCard,
                        shape = SimbanyuTheme.shapes.extraLarge
                    )
                    .padding(
                        SimbanyuTheme.spacing.extraLarge,
                        SimbanyuTheme.spacing.extraExtraLarge

                    ),

                verticalArrangement = Arrangement.Center
            ) {
                SimbanyuText(
                    text = "Selamat Datang",
                    style = SimbanyuTheme.typography.titlePage,
                )

                SimbanyuText(
                    modifier = Modifier.padding(bottom = SimbanyuTheme.padding.extraLarge),
                    text = "Masuk untuk mengelola layanan air anda",
                    style = SimbanyuTheme.typography.bodyStandard
                )

                if (!state.errorMessage.isEmpty()) {
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .clip(
                                shape = SimbanyuTheme.shapes.large
                            )
                            .background(
                                color = SimbanyuTheme.colors.statusNegative,
                            )
                            .leftBorder(
                                SimbanyuTheme.colors.statusNegativeText,
                                SimbanyuTheme.padding.medium
                            )
                            .padding(
                                vertical = SimbanyuTheme.padding.large,
                                horizontal = SimbanyuTheme.padding.large
                            ),
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.spacedBy(
                            SimbanyuTheme.padding.medium
                        )
                    ) {
                        Icon(
                            painter = painterResource(R.drawable.ic_error),
                            contentDescription = "Icon",
                            tint = SimbanyuTheme.colors.statusNegativeText
                        )
                        SimbanyuText(
                            text = state.errorMessage,
                            color = SimbanyuTheme.colors.statusNegativeText,
                        )
                    }
                    Spacer(
                        modifier = Modifier.height(
                            SimbanyuTheme.spacing.large
                        )
                    )
                }

                SimbanyuText(
                    modifier = Modifier.padding(bottom = SimbanyuTheme.padding.medium),
                    text = "USERNAME / NO. HP / NO. METER",
                    style = SimbanyuTheme.typography.labelOverline
                )

                SimbanyuTextField(
                    modifier = Modifier,
                    state = state.username,
                    hint = "Masukkan identitas anda",
                    prefix = {
                        Icon(
                            painter = painterResource(R.drawable.ic_person_filled),
                            contentDescription = "Username",
                            tint = SimbanyuTheme.colors.textInputDisabled
                        )
                    },
                    imeAction = ImeAction.Next
                )

                SimbanyuText(
                    modifier = Modifier.padding(
                        top = SimbanyuTheme.padding.large,
                        bottom = SimbanyuTheme.padding.medium
                    ),
                    text = "PASSWORD",
                    style = SimbanyuTheme.typography.labelOverline
                )

                SimbanyuTextField(
                    modifier = Modifier,
                    state = state.password,
                    hint = "Password anda",
                    isPassword = true,
                    isPasswordVisible = state.isPasswordVisible,
                    prefix = {
                        Icon(
                            painter = painterResource(R.drawable.ic_lock_filled),
                            contentDescription = "Login",
                            tint = SimbanyuTheme.colors.textInputDisabled
                        )
                    },
                    suffix = {
                        if (state.isPasswordVisible) {
                            Icon(
                                painter = painterResource(R.drawable.ic_visibility_off_filled),
                                contentDescription = "Hide password",
                                tint = SimbanyuTheme.colors.textInputDisabled,
                                modifier = Modifier
                                    .clickable {
                                        onAction(LoginAction.TogglePasswordVisibility)
                                    }
                            )
                        } else {
                            Icon(
                                painter = painterResource(R.drawable.ic_visibility_filled),
                                contentDescription = "Show password",
                                tint = SimbanyuTheme.colors.textInputDisabled,
                                modifier = Modifier
                                    .clickable {
                                        onAction(LoginAction.TogglePasswordVisibility)
                                    })
                        }
                    },
                    imeAction = ImeAction.Go,
                    onKeyboardAction = {
                        tryLogin()
                    }
                )

                Spacer(modifier = Modifier.height(SimbanyuTheme.spacing.extraLarge))

                SimbanyuButton(
                    modifier = Modifier
                        .fillMaxWidth()
                        .background(
                            brush = SimbanyuTheme.colors.brush.brandVertical
                        ),
                    text = "Login",
                    iconRight = {
                        if (state.isLoading) {
                            CircularProgressIndicator(
                                modifier = Modifier.size(18.dp),
                                strokeWidth = 2.dp,
                                color = SimbanyuTheme.colors.actionBrandText
                            )
                        } else {
                            Icon(
                                modifier = Modifier.size(18.dp),
                                painter = painterResource(R.drawable.ic_login),
                                contentDescription = "Login",
                                tint = SimbanyuTheme.colors.actionBrandText
                            )
                        }
                    },
                ) {
                    tryLogin()
                }
            }

            Spacer(
                modifier = Modifier
                    .height(SimbanyuTheme.spacing.medium)
                    .fillMaxWidth()
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun LoginScreenPreview() {
    LoginContent(
        state = LoginUiState(),
    )
}