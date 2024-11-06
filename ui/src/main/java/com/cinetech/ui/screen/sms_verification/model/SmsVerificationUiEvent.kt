package com.cinetech.ui.screen.sms_verification.model

import com.cinetech.ui.base.Reducer

sealed class SmsVerificationUiEvent: Reducer.ViewEvent {
    class OnSmsCodeTextChange(val newValue: String) : SmsVerificationUiEvent()
    class Loading(val isLoading: Boolean): SmsVerificationUiEvent()
    class ShowError(val rId: Int): SmsVerificationUiEvent()
}