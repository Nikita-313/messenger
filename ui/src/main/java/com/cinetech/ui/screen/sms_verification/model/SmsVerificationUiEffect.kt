package com.cinetech.ui.screen.sms_verification.model

import com.cinetech.ui.base.Reducer

sealed class SmsVerificationUiEffect : Reducer.ViewEffect  {
    data object SmsCodeInvalid : SmsVerificationUiEffect()
}