package com.cinetech.ui.screen.auth.model

import androidx.compose.ui.text.input.TextFieldValue
import com.cinetech.ui.base.Reducer

sealed class AuthUiEvent : Reducer.ViewEvent {
    class OnCountryCodeTextChange(val newValue: TextFieldValue, val country: Country?) : AuthUiEvent()
    class OnPhoneNumberTextChange(val newValue: TextFieldValue) : AuthUiEvent()
    class Loading(val isLoading: Boolean): AuthUiEvent()
    class ShowError(val rId: Int): AuthUiEvent()
}