package com.avivba.simbanyu.ui.screens.login

import android.annotation.SuppressLint
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.input.rememberTextFieldState
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.avivba.simbanyu.R
import com.avivba.simbanyu.ui.components.buttons.SimbanyuButton
import com.avivba.simbanyu.ui.components.textfield.SimbanyuTextField

@Preview
@Composable
fun LoginScreen() {
    Scaffold() { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .background(MaterialTheme.colorScheme.background),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            val usernameState = rememberTextFieldState()
            val passwordState = rememberTextFieldState()
            val isPasswordVisible = remember { mutableStateOf(false) }
            val toggleVisibility = { isPasswordVisible.value = !isPasswordVisible.value }

            SimbanyuTextField(
                modifier = Modifier
                    .padding(8.dp),
                state = usernameState,
                hint = "Username"
            )
            SimbanyuTextField(
                modifier = Modifier
                    .padding(8.dp),
                state = passwordState,
                hint = "Password",
                isPassword = true,
                isPasswordVisible = isPasswordVisible.value,
                suffix = {
                    if (isPasswordVisible.value) {
                        Text("Hide", modifier = Modifier.clickable { toggleVisibility() })
                    } else {
                        Text("Show", modifier = Modifier.clickable { toggleVisibility() })
                    }
                }
            )
            SimbanyuButton(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp, 0.dp),
                text = "Login",
                iconRight = {
                    Icon(
                        painter = painterResource(R.drawable.ic_login),
                        contentDescription = "Login"
                    )
                }
            ) { }
        }
    }
}