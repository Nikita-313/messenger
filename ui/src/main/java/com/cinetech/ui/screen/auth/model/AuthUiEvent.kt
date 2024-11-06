package com.cinetech.ui.screen.auth.model

import com.cinetech.ui.base.Reducer

sealed class AuthUiEvent : Reducer.ViewEvent {
    class OnCountryCodeTextChange(val newValue: String, val country: Country?) : AuthUiEvent()
    class OnPhoneNumberTextChange(val newValue: String) : AuthUiEvent()
}