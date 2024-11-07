package com.cinetech.ui.screen.registration.model

import com.cinetech.ui.base.Reducer

sealed class RegistrationUiEvent: Reducer.ViewEvent {
    class OnNameTextChange(val newValue: String) : RegistrationUiEvent()
    class OnUserNameTextChange(val newValue: String) : RegistrationUiEvent()
    class Loading(val isLoading: Boolean): RegistrationUiEvent()
}