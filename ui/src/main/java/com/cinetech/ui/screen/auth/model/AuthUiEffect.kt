package com.cinetech.ui.screen.auth.model

import com.cinetech.ui.base.Reducer

sealed class AuthUiEffect : Reducer.ViewEffect {
    data object PhoneNumberInvalid : AuthUiEffect()
}