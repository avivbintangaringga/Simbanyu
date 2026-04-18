package com.avivba.simbanyu.ui.components.textfield

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.BasicSecureTextField
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.text.input.KeyboardActionHandler
import androidx.compose.foundation.text.input.TextFieldDecorator
import androidx.compose.foundation.text.input.TextFieldLineLimits
import androidx.compose.foundation.text.input.TextFieldState
import androidx.compose.foundation.text.input.TextObfuscationMode
import androidx.compose.foundation.text.input.rememberTextFieldState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.tooling.preview.Preview
import com.avivba.simbanyu.ui.components.text.SimbanyuText
import com.avivba.simbanyu.ui.theme.Grey90
import com.avivba.simbanyu.ui.theme.SimbanyuTheme

@Composable
fun SimbanyuTextField(
    modifier: Modifier = Modifier,
    state: TextFieldState,
    hint: String,
    prefix: @Composable (() -> Unit)? = null,
    suffix: @Composable (() -> Unit)? = null,
    isPassword: Boolean = false,
    lineLimits: TextFieldLineLimits = TextFieldLineLimits.SingleLine,
    imeAction: ImeAction = ImeAction.Default,
    isPasswordVisible: Boolean = false,
    onKeyboardAction: KeyboardActionHandler? = null,
) {
    Row(
        modifier = modifier
            .fillMaxWidth()
            .clip(SimbanyuTheme.shapes.large)
            .background(SimbanyuTheme.colors.surfaceInput)
            .padding(
                horizontal = SimbanyuTheme.padding.large,
                vertical = SimbanyuTheme.padding.medium.plus(
                    SimbanyuTheme.padding.small
                )
            ),
        horizontalArrangement = Arrangement.spacedBy(SimbanyuTheme.spacing.large),
        verticalAlignment = Alignment.CenterVertically
    ) {
        val modifier2 = Modifier.fillMaxWidth()
        val decorator = TextFieldDecorator { innerTextField ->
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(SimbanyuTheme.spacing.large),
                verticalAlignment = Alignment.CenterVertically
            ) {
                prefix?.invoke()
                Box(
                    modifier = Modifier.weight(1f),
                    contentAlignment = Alignment.TopStart
                ) {
                    innerTextField()
                    if (state.text.isEmpty()) {
                        SimbanyuText(
                            text = hint,
                            style = SimbanyuTheme.typography.bodyStandard,
                            color = SimbanyuTheme.colors.textInputDisabled
                        )
                    }
                }
                suffix?.invoke()
            }
        }

        if (isPassword) {
            BasicSecureTextField(
                state = state,
                modifier = modifier2,
                textStyle = SimbanyuTheme.typography.bodyStandard.copy(
                    color = SimbanyuTheme.colors.textInput
                ),
                decorator = decorator,
                keyboardOptions = KeyboardOptions(
                    imeAction = imeAction
                ),
                textObfuscationMode =
                    if (isPasswordVisible)
                        TextObfuscationMode.Visible
                    else
                        TextObfuscationMode.Hidden,
                onKeyboardAction = onKeyboardAction
            )
        } else {
            BasicTextField(
                state = state,
                modifier = modifier2,
                textStyle = SimbanyuTheme.typography.bodyStandard.copy(
                    color = SimbanyuTheme.colors.textInput
                ),
                lineLimits = lineLimits,
                decorator = decorator,
                keyboardOptions = KeyboardOptions(
                    imeAction = imeAction
                ),
                onKeyboardAction = onKeyboardAction
            )
        }

    }
}

@Preview
@Composable
fun PreviewTextInput() {
    val state = rememberTextFieldState()
    SimbanyuTextField(
        state = state,
        hint = "Username"
    )
}
