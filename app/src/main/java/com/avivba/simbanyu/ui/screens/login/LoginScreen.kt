package com.avivba.simbanyu.ui.screens.login

import android.annotation.SuppressLint
import android.content.Context
import android.widget.Toast
import androidx.compose.foundation.Image
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
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.input.KeyboardActionHandler
import androidx.compose.foundation.text.input.rememberTextFieldState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.dropShadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.shadow.Shadow
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalContext
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
import com.avivba.simbanyu.ui.theme.Grey30
import com.avivba.simbanyu.ui.theme.plusJakartaSansRegular
import com.avivba.simbanyu.ui.utils.leftBorder

@Preview
@Composable
fun LoginScreen() {
    val context = LocalContext.current
    var isWrongCredentials by remember { mutableStateOf(false) }
    val scrollState = rememberScrollState()
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
                .verticalScroll(scrollState)
                .padding(24.dp),
            verticalArrangement = Arrangement.SpaceEvenly,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Column(
                modifier = Modifier.padding(bottom = 16.dp),
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
                        MaterialTheme.colorScheme.surface, RoundedCornerShape(16.dp)
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
                    fontSize = 14.sp,
                    color = Grey30
                )

                if (isWrongCredentials) {
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(bottom = 16.dp)
                            .clip(RoundedCornerShape(16.dp))
                            .background(Color.Red.copy(alpha = 0.2f))
                            .leftBorder(Color.Red, 8.dp)
                            .padding(16.dp),
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.spacedBy(8.dp)
                    ) {
                        Icon(
                            painter = painterResource(R.drawable.ic_error),
                            contentDescription = "Icon",
                            tint = Color.Red
                        )
                        Text(
                            text = "Username atau password salah!",
                            color = Color.Red,
                            fontSize = 12.sp
                        )
                    }
                }

                Text(
                    modifier = Modifier.padding(bottom = 8.dp),
                    text = "USERNAME / NO. HP / NO. METER",
                    fontWeight = FontWeight.ExtraBold,
                    fontSize = 12.sp,
                    color = Grey30
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
                    fontSize = 12.sp,
                    color = Grey30
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
                    imeAction = ImeAction.Go,
                    onKeyboardAction = {
                        doLogin(context)
                    }
                )

                Spacer(modifier = Modifier.height(24.dp))

                SimbanyuButton(
                    modifier = Modifier.fillMaxWidth(), text = "Login", iconRight = {
                        Icon(
                            modifier = Modifier.height(18.dp),
                            painter = painterResource(R.drawable.ic_login),
                            contentDescription = "Login"
                        )
                    }
                ) {
                    doLogin(context)
                    isWrongCredentials = true
                }
            }

            Spacer(
                modifier = Modifier
                    .height(8.dp)
                    .fillMaxWidth()
            )
        }
    }
}

fun doLogin(context: Context) {
    Toast.makeText(context, "LOGIN", Toast.LENGTH_SHORT).show()
}