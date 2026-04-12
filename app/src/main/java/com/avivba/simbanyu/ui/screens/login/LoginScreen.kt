package com.avivba.simbanyu.ui.screens.login

import android.annotation.SuppressLint
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
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
import androidx.compose.ui.draw.dropShadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.shadow.Shadow
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.avivba.simbanyu.R
import com.avivba.simbanyu.ui.components.buttons.SimbanyuButton
import com.avivba.simbanyu.ui.components.textfield.SimbanyuTextField
import com.avivba.simbanyu.ui.theme.Blue30
import com.avivba.simbanyu.ui.theme.Blue95
import com.avivba.simbanyu.ui.theme.plusJakartaSansRegular

@Preview
@Composable
fun LoginScreen() {
    val usernameState = rememberTextFieldState()
    val passwordState = rememberTextFieldState()
    val isPasswordVisible = remember { mutableStateOf(false) }
    val toggleVisibility = { isPasswordVisible.value = !isPasswordVisible.value }

    Scaffold() { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(Blue95)
                .padding(paddingValues)
                .padding(24.dp),
            verticalArrangement = Arrangement.SpaceEvenly,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Column(
                modifier = Modifier.padding(),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                Image(
                    painter = painterResource(R.drawable.logo),
                    contentDescription = "Logo",
                    modifier = Modifier.height(64.dp)
                )
                Text(
                    text = "SIMBANYU",
                    style = MaterialTheme.typography.headlineLarge,
                    fontWeight = FontWeight.Bold,
                    color = Blue30,
                    fontFamily = plusJakartaSansRegular
                )
                Text(
                    text = "Manajemen Sumber Daya Air"
                )
            }
            Column(
                modifier = Modifier
                    .dropShadow(
                        RoundedCornerShape(16.dp), Shadow(8.dp, alpha = 0.1f)
                    )
                    .background(
                        MaterialTheme.colorScheme.surfaceContainer, RoundedCornerShape(16.dp)
                    )
                    .padding(24.dp),

                verticalArrangement = Arrangement.Center
            ) {
                Text(
                    text = "Selamat Datang",
                    style = MaterialTheme.typography.headlineSmall,
                    fontWeight = FontWeight.Bold,
                    fontFamily = plusJakartaSansRegular
                )

                Text(
                    modifier = Modifier.padding(bottom = 24.dp),
                    text = "Masuk untuk mengelola layanan air anda",
                    fontSize = 14.sp
                )

                Text(
                    modifier = Modifier.padding(bottom = 8.dp),
                    text = "USERNAME / NO. HP / NO. METER",
                    fontWeight = FontWeight.ExtraBold,
                    fontSize = 12.sp
                )

                SimbanyuTextField(
                    modifier = Modifier,
                    state = usernameState,
                    hint = "Masukkan identitas anda",
                    prefix = {
                        Icon(
                            painter = painterResource(R.drawable.ic_person),
                            contentDescription = "Username",
                        )
                    },
                    imeAction = ImeAction.Next
                )

                Text(
                    modifier = Modifier.padding(top = 16.dp, bottom = 8.dp),
                    text = "PASSWORD",
                    fontWeight = FontWeight.ExtraBold,
                    fontSize = 12.sp
                )

                SimbanyuTextField(
                    modifier = Modifier,
                    state = passwordState,
                    hint = "Password anda",
                    isPassword = true,
                    isPasswordVisible = isPasswordVisible.value,
                    prefix = {
                        Icon(
                            painter = painterResource(R.drawable.ic_lock),
                            contentDescription = "Login"
                        )
                    },
                    suffix = {
                        if (isPasswordVisible.value) {
                            Icon(
                                painter = painterResource(R.drawable.ic_visibility_off),
                                contentDescription = "Hide password",
                                modifier = Modifier.clickable { toggleVisibility() })
                        } else {
                            Icon(
                                painter = painterResource(R.drawable.ic_visibility_on),
                                contentDescription = "Show password",
                                modifier = Modifier.clickable { toggleVisibility() })
                        }
                    },
                    imeAction = ImeAction.Go
                )

                Spacer(modifier = Modifier.height(16.dp))

                SimbanyuButton(
                    modifier = Modifier.fillMaxWidth(), text = "Login", iconRight = {
                        Icon(
                            modifier = Modifier.height(16.dp),
                            painter = painterResource(R.drawable.ic_login),
                            contentDescription = "Login"
                        )
                    }) { }
            }

            Spacer(modifier = Modifier.height(8.dp).fillMaxWidth())
        }
    }
}