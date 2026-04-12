package com.avivba.simbanyu.ui.components.textfield

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicSecureTextField
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.text.input.KeyboardActionHandler
import androidx.compose.foundation.text.input.OutputTransformation
import androidx.compose.foundation.text.input.TextFieldBuffer
import androidx.compose.foundation.text.input.TextFieldDecorator
import androidx.compose.foundation.text.input.TextFieldLineLimits
import androidx.compose.foundation.text.input.TextFieldState
import androidx.compose.foundation.text.input.TextObfuscationMode
import androidx.compose.foundation.text.input.insert
import androidx.compose.foundation.text.input.rememberTextFieldState
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.ShapeDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.blur
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.key.Key
import androidx.compose.ui.layout.ModifierLocalBeyondBoundsLayout
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.avivba.simbanyu.ui.theme.Grey20
import com.avivba.simbanyu.ui.theme.Grey50
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
            .clip(RoundedCornerShape(16.dp))
            .background(Grey90)
            .padding(16.dp, 12.dp),
        horizontalArrangement = Arrangement.spacedBy(16.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        val modifier2 = Modifier.fillMaxWidth()
        val decorator = TextFieldDecorator { innerTextField ->
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(16.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                prefix?.invoke()
                Box(
                    modifier = Modifier.weight(1f),
                    contentAlignment = Alignment.CenterStart
                ) {
                    innerTextField()
                    if (state.text.isEmpty()) {
                        Text(
                            text = hint,
                            style = SimbanyuTheme.typography.textField
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
                textStyle = SimbanyuTheme.typography.textField,
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
                textStyle = SimbanyuTheme.typography.textField,
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