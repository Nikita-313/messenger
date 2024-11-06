package com.cinetech.ui.screen.sms_verification

import androidx.core.text.isDigitsOnly
import com.cinetech.ui.base.BaseViewModel
import com.cinetech.ui.screen.sms_verification.model.SmsVerificationUiEffect
import com.cinetech.ui.screen.sms_verification.model.SmsVerificationUiEvent
import com.cinetech.ui.screen.sms_verification.model.SmsVerificationUiState
import javax.inject.Inject

class SmsVerificationViewModel @Inject constructor() : BaseViewModel<SmsVerificationUiState, SmsVerificationUiEvent, SmsVerificationUiEffect>(
    initialState = SmsVerificationUiState(),
    reducer = SmsVerificationReducer(),
) {

    fun onSmsCodeTextChange(newValue: String) {
        if (newValue.isDigitsOnly() && newValue.length <= 6) {
            sendEvent(SmsVerificationUiEvent.OnSmsCodeTextChange(newValue))
        }
    }

    fun sendSmsCode(){
        if(!isSmsCodeValid()) {
            sendEffect(SmsVerificationUiEffect.SmsCodeInvalid)
        }
    }

    private fun isSmsCodeValid():Boolean{
        if (state.value.smsCode.isEmpty()) return false
        if(state.value.smsCode.length < 6) return false
        return true
    }

}