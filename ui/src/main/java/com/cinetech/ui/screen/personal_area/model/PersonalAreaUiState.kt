package com.cinetech.ui.screen.personal_area.model

import androidx.compose.ui.graphics.ImageBitmap
import com.cinetech.domain.model.User
import com.cinetech.ui.base.Reducer

data class PersonalAreaUiState(
    val user: User? = null,
    val editUser: User? = null,

    val userImage: ImageBitmap? = null,
    val editUserImage: ImageBitmap? = null,

    val isLoading: Boolean = true,
    val isEditModeEnabled: Boolean = false,
    val isShowDatePiker: Boolean = false,
) : Reducer.ViewState