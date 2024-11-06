package com.cinetech.ui.screen.sms_verification

import com.cinetech.ui.base.Reducer
import com.cinetech.ui.screen.sms_verification.model.SmsVerificationUiEffect
import com.cinetech.ui.screen.sms_verification.model.SmsVerificationUiEvent
import com.cinetech.ui.screen.sms_verification.model.SmsVerificationUiState


class SmsVerificationReducer : Reducer<SmsVerificationUiState, SmsVerificationUiEvent, SmsVerificationUiEffect> {

    override fun reduce(previousState: SmsVerificationUiState, event: SmsVerificationUiEvent): Pair<SmsVerificationUiState, SmsVerificationUiEffect?> {
        return when (event) {
            is SmsVerificationUiEvent.OnSmsCodeTextChange -> {
                previousState.copy(smsCode = event.newValue) to null
            }

            is SmsVerificationUiEvent.Loading -> {
                previousState.copy(isLoading = event.isLoading) to null
            }

            is SmsVerificationUiEvent.ShowError -> {
                previousState.copy(errorTextRId = event.rId) to  SmsVerificationUiEffect.SmsCodeInvalid
            }
        }
    }

}