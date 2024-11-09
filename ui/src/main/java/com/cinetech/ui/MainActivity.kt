package com.cinetech.ui

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.compose.material3.Scaffold
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import com.cinetech.ui.navigation.MessengerNavHost
import com.cinetech.ui.navigation.Screen
import com.cinetech.ui.theme.MessengerTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    private val viewModel by viewModels<MainViewModel>()

    @SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        installSplashScreen().apply {
            setKeepOnScreenCondition {
                !viewModel.isReady.value
            }
        }
        setContent {
            if (viewModel.isReady.value)
                MessengerTheme {
                    Scaffold {
                        val startScreen = if (viewModel.isAuth.value) Screen.Main else Screen.AuthGraph
                        MessengerNavHost(startScreen)
                    }
                }
        }
    }
}
