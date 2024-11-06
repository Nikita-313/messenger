package com.cinetech.ui.screen.auth.model

import androidx.compose.ui.text.input.TextFieldValue
import com.cinetech.ui.base.Reducer

data class AuthUiState(
    val country: Country? = null,
    val countyCode: TextFieldValue = TextFieldValue(""),
    val phoneNumber: TextFieldValue = TextFieldValue(""),
    val isLoading: Boolean = false,
    val errorTextRId: Int? = null,
) : Reducer.ViewState
