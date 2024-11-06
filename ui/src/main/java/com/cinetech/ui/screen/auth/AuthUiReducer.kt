package com.cinetech.ui.screen.auth

import com.cinetech.ui.base.Reducer
import com.cinetech.ui.screen.auth.model.AuthUiEffect
import com.cinetech.ui.screen.auth.model.AuthUiEvent
import com.cinetech.ui.screen.auth.model.AuthUiState

class AuthUiReducer : Reducer<AuthUiState, AuthUiEvent, AuthUiEffect> {
    override fun reduce(previousState: AuthUiState, event: AuthUiEvent): Pair<AuthUiState, AuthUiEffect?> {
        return when (event) {
            is AuthUiEvent.OnCountryCodeTextChange -> {
                previousState.copy(countyCode = event.newValue, country = event.country, errorTextRId = null) to null
            }
            is AuthUiEvent.OnPhoneNumberTextChange -> {
                previousState.copy(phoneNumber = event.newValue, errorTextRId = null) to null
            }
            is AuthUiEvent.Loading ->{
                previousState.copy(isLoading = event.isLoading) to null
            }

            is AuthUiEvent.ShowError -> {
                previousState.copy(errorTextRId = event.rId) to AuthUiEffect.PhoneNumberInvalid
            }
        }
    }
}