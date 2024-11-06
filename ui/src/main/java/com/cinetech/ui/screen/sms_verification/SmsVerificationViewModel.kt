package com.cinetech.ui.screen.sms_verification

import android.util.Log
import androidx.core.text.isDigitsOnly
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.viewModelScope
import androidx.navigation.toRoute
import com.cinetech.domain.exeption.NotFoundException
import com.cinetech.domain.exeption.ValidationException
import com.cinetech.domain.repository.AuthRepository
import com.cinetech.domain.utils.Response
import com.cinetech.ui.R
import com.cinetech.ui.base.BaseViewModel
import com.cinetech.ui.navigation.Screen
import com.cinetech.ui.screen.sms_verification.model.SmsVerificationUiEffect
import com.cinetech.ui.screen.sms_verification.model.SmsVerificationUiEvent
import com.cinetech.ui.screen.sms_verification.model.SmsVerificationUiState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import java.net.UnknownHostException
import javax.inject.Inject

@HiltViewModel
class SmsVerificationViewModel @Inject constructor(
    savedState: SavedStateHandle,
    private val authRepository: AuthRepository,
) : BaseViewModel<SmsVerificationUiState, SmsVerificationUiEvent, SmsVerificationUiEffect>(
    initialState = SmsVerificationUiState(),
    reducer = SmsVerificationReducer(),
) {
    private val phoneNumber = savedState.toRoute<Screen.SmsVerification>().phone

    fun onSmsCodeTextChange(newValue: String) {
        if (state.value.isLoading) return
        if (newValue.isDigitsOnly() && newValue.length <= 6) {
            sendEvent(SmsVerificationUiEvent.OnSmsCodeTextChange(newValue))
        }
    }

    fun checkSmsCode(){
        if(!isSmsCodeValid()) {
            sendEffect(SmsVerificationUiEffect.SmsCodeInvalid)
        }

        viewModelScope.launch {
            authRepository.checkAuthCode(phone = phoneNumber, code = state.value.smsCode).collect { response ->
                when (response){
                    is Response.Error -> {
                        sendEvent(SmsVerificationUiEvent.Loading(false))
                        errorHandler(response.throwable)
                    }
                    Response.Loading -> {
                        sendEvent(SmsVerificationUiEvent.Loading(true))
                    }
                    is Response.Success -> {
                        sendEvent(SmsVerificationUiEvent.Loading(false))
                    }
                    Response.Timeout -> { sendEvent(SmsVerificationUiEvent.Loading(false)) }
                }
            }
        }
    }

    private fun errorHandler(throwable: Throwable?) {
        if (throwable == null) return
        Log.e("SmsVerificationViewModel errorHandler", throwable.stackTraceToString())
        when (throwable) {
            is UnknownHostException -> {
                sendEventForEffect(SmsVerificationUiEvent.ShowError(R.string.exception_no_internet_connection))
            }
            is ValidationException -> {
                sendEventForEffect(SmsVerificationUiEvent.ShowError(R.string.exception_validation_error))
            }
            is NotFoundException -> {
                sendEventForEffect(SmsVerificationUiEvent.ShowError(R.string.exception_invalid_sms_code))
            }
            else -> {
                sendEventForEffect(SmsVerificationUiEvent.ShowError(R.string.exception_unknown))
            }
        }
    }

    private fun isSmsCodeValid():Boolean{
        if (state.value.smsCode.isEmpty()) return false
        if(state.value.smsCode.length < 6) return false
        return true
    }

}