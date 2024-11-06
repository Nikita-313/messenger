package com.cinetech.ui.navigation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.navigation
import androidx.navigation.compose.rememberNavController
import com.cinetech.ui.screen.auth.AuthScreen
import com.cinetech.ui.screen.auth.SelectCountryCodeScreen

@Composable
fun MessengerNavHost(
    navHostController: NavHostController = rememberNavController(),
) {
    NavHost(
        navController = navHostController,
        startDestination = Screen.AuthGraph
    ) {

        navigation<Screen.AuthGraph>(startDestination = Screen.Auth) {

            composable<Screen.Auth> {
                val parentEntry = remember(it) { navHostController.getBackStackEntry(Screen.AuthGraph) }
                AuthScreen(
                    viewModel = hiltViewModel(parentEntry),
                    onNavigate = { screen -> navHostController.navigate(screen) }
                )
            }
            composable<Screen.SelectCountryCode> {
                val parentEntry = remember(it) { navHostController.getBackStackEntry(Screen.AuthGraph) }
                SelectCountryCodeScreen(
                    viewModel = hiltViewModel(parentEntry),
                    onPop = { navHostController.popBackStack() }
                )
            }
        }

    }
}