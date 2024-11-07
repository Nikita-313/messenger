package com.cinetech.ui.screen.registration.model

import com.cinetech.ui.base.Reducer

sealed class RegistrationUiEffect : Reducer.ViewEffect {
    data object NameInvalid : RegistrationUiEffect()
    data object UserNameInvalid : RegistrationUiEffect()
    data class ShowToast(val rId: Int) : RegistrationUiEffect()
}