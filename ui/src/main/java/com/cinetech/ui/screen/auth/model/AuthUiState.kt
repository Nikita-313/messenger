package com.cinetech.ui.screen.auth.model

import com.cinetech.ui.base.Reducer

data class AuthUiState(
    val country: Country? = null,
    val countyCode: String = "",
    val phoneNumber: String = "",
) : Reducer.ViewState
