package com.cinetech.ui.screen.sms_verification.model

import com.cinetech.ui.base.Reducer

data class SmsVerificationUiState(
    val smsCode: String = "",
    val isLoading:Boolean = false,
    val errorTextRId: Int? = null,
) : Reducer.ViewState