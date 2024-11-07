package com.cinetech.ui.screen.registration

import android.util.Log
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.viewModelScope
import androidx.navigation.toRoute
import com.cinetech.domain.exeption.BadRequestException
import com.cinetech.domain.exeption.ValidationException
import com.cinetech.domain.model.RegisterUserData
import com.cinetech.domain.repository.RegistrationRepository
import com.cinetech.domain.utils.Response
import com.cinetech.ui.R
import com.cinetech.ui.base.BaseViewModel
import com.cinetech.ui.navigation.Screen
import com.cinetech.ui.screen.registration.model.RegistrationUiEffect
import com.cinetech.ui.screen.registration.model.RegistrationUiEvent
import com.cinetech.ui.screen.registration.model.RegistrationUiState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import java.net.UnknownHostException
import javax.inject.Inject

@HiltViewModel
class RegistrationViewModel @Inject constructor(
    savedState: SavedStateHandle,
    private val registrationRepository: RegistrationRepository
) : BaseViewModel<RegistrationUiState, RegistrationUiEvent, RegistrationUiEffect>(
    initialState = RegistrationUiState(),
    reducer = RegistrationReducer(),
) {
    val phoneNumber = savedState.toRoute<Screen.Registration>().phone
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
        val registerUserData = RegisterUserData(
            phone = phoneNumber,
            name = state.value.name,
            userName = state.value.userName
        )
        viewModelScope.launch {
            registrationRepository.registerUser(registerUserData).collect{ response->
                when(response){
                    is Response.Error -> {
                        sendEvent(RegistrationUiEvent.Loading(false))
                        errorHandler(response.throwable)
                    }
                    Response.Loading -> {
                        sendEvent(RegistrationUiEvent.Loading(true))
                    }
                    is Response.Success -> {
                        sendEvent(RegistrationUiEvent.Loading(false))
                    }
                    Response.Timeout -> {
                        sendEvent(RegistrationUiEvent.Loading(false))
                    }
                }
            }
        }
    }

    private fun errorHandler(throwable: Throwable?) {
        if (throwable == null) return
        Log.e("RegistrationViewModel errorHandler", throwable.stackTraceToString())
        when (throwable) {
            is UnknownHostException -> {
                sendEffect(RegistrationUiEffect.ShowToast(R.string.exception_no_internet_connection))
            }
            is ValidationException -> {
                sendEffect(RegistrationUiEffect.ShowToast(R.string.exception_validation_error))
            }
            is BadRequestException -> {
                sendEffect(RegistrationUiEffect.ShowToast(R.string.registration_exception_bad_request))
            }
            else -> {
                sendEffect(RegistrationUiEffect.ShowToast(R.string.exception_unknown))
            }
        }
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