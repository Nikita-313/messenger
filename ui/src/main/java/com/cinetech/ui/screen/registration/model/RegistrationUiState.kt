package com.cinetech.ui.screen.registration.model

import com.cinetech.ui.base.Reducer

data class RegistrationUiState(
    val name: String = "",
    val userName: String = "",
    val isLoading:Boolean = false,
) : Reducer.ViewState