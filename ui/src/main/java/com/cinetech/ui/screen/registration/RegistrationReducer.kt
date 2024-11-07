package com.cinetech.ui.screen.registration

import com.cinetech.ui.base.Reducer
import com.cinetech.ui.screen.registration.model.RegistrationUiEffect
import com.cinetech.ui.screen.registration.model.RegistrationUiEvent
import com.cinetech.ui.screen.registration.model.RegistrationUiState


class RegistrationReducer : Reducer<RegistrationUiState, RegistrationUiEvent, RegistrationUiEffect> {
    override fun reduce(previousState: RegistrationUiState, event: RegistrationUiEvent): Pair<RegistrationUiState, RegistrationUiEffect?> {
        return when (event) {
            is RegistrationUiEvent.Loading -> {
                previousState.copy(isLoading = event.isLoading) to null
            }

            is RegistrationUiEvent.OnNameTextChange -> {
                previousState.copy(name = event.newValue) to null
            }

            is RegistrationUiEvent.OnUserNameTextChange -> {
                previousState.copy(userName = event.newValue) to null
            }

        }
    }
}