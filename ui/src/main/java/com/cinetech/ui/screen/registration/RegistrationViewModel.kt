package com.cinetech.ui.screen.registration

import com.cinetech.ui.base.BaseViewModel
import com.cinetech.ui.screen.registration.model.RegistrationUiEffect
import com.cinetech.ui.screen.registration.model.RegistrationUiEvent
import com.cinetech.ui.screen.registration.model.RegistrationUiState
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class RegistrationViewModel @Inject constructor() : BaseViewModel<RegistrationUiState, RegistrationUiEvent, RegistrationUiEffect>(
    initialState = RegistrationUiState(),
    reducer = RegistrationReducer(),
) {
    private val usernameRegex = "^[A-Za-z0-9-_]*$".toRegex()

    fun onNameChange(newValue: String) {
        if (state.value.isLoading) return
        sendEvent(RegistrationUiEvent.OnNameTextChange(newValue))
    }

    fun onUserNameChange(newValue: String) {
        if (state.value.isLoading) return
        if (!newValue.matches(usernameRegex)) return
        sendEvent(RegistrationUiEvent.OnUserNameTextChange(newValue))
    }

    fun registerUser() {
        if (!checkFormValidAndShowErrors()) return

    }


    private fun checkFormValidAndShowErrors(): Boolean {
        var isValid = true

        if (state.value.name.isEmpty()) {
            sendEffect(RegistrationUiEffect.NameInvalid)
            isValid = false
        }

        if (state.value.userName.isEmpty()) {
            sendEffect(RegistrationUiEffect.UserNameInvalid)
            isValid = false
        }

        return isValid
    }

}