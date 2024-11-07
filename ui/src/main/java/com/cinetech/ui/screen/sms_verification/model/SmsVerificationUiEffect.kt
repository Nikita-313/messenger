package com.cinetech.ui.screen.sms_verification.model

import com.cinetech.ui.base.Reducer
import com.cinetech.ui.navigation.Screen

sealed class SmsVerificationUiEffect : Reducer.ViewEffect  {
    data object SmsCodeInvalid : SmsVerificationUiEffect()
    data class NavigateTo(val screen: Screen) : SmsVerificationUiEffect()
    class ShowToast(val rId: Int): SmsVerificationUiEffect()
}