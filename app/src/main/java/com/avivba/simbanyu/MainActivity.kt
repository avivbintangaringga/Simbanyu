package com.avivba.simbanyu

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import com.avivba.simbanyu.ui.screens.login.LoginScreen
import com.avivba.simbanyu.ui.theme.SimbanyuTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            SimbanyuTheme() {
                LoginScreen()
            }
        }
    }
}