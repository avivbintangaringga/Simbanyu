package com.avivba.simbanyu.ui.activities

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.runtime.getValue
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.avivba.simbanyu.ui.shared.Screen
import com.avivba.simbanyu.ui.shared.SimbanyuApp
import com.avivba.simbanyu.ui.theme.SimbanyuTheme
import org.koin.androidx.viewmodel.ext.android.viewModel

class MainActivity : ComponentActivity() {
    val viewModel: MainActivityViewModel by viewModel()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            val isLoggedIn by viewModel.isLoggedIn.collectAsStateWithLifecycle()
            val profile by viewModel.profile.collectAsStateWithLifecycle()

            SimbanyuTheme {
                val dest = if (isLoggedIn && profile != null) {
                    when (profile!!.role) {
                        "OPERATOR" -> Screen.OperatorMain
                        "SUBSCRIBER" -> Screen.Main
                        else -> Screen.Login
                    }
                } else Screen.Login

                SimbanyuApp(
                    startScreen = dest
                )
            }
        }
    }
}