package com.cinetech.ui.screen.auth.model

import com.cinetech.ui.base.Reducer
import com.cinetech.ui.navigation.Screen

sealed class AuthUiEffect : Reducer.ViewEffect {
    data object PhoneNumberInvalid : AuthUiEffect()
    data class NavigateTo(val screen: Screen) : AuthUiEffect()
}