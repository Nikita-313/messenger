package com.cinetech.ui.screen.personal_area.model

import androidx.compose.ui.graphics.ImageBitmap
import com.cinetech.domain.model.AvatarData
import com.cinetech.domain.model.User
import com.cinetech.ui.base.Reducer

data class PersonalAreaUiState(
    val user: User? = null,
    val userImage: ImageBitmap? = null,
    val newUserImage: ImageBitmap? = null,

    val editUser: User? = null,
    val updateAvatarData: AvatarData? = null,

    val isEditModeEnabled: Boolean = false,
    val isShowDatePiker: Boolean = false,
    val isUpdateDataLoading: Boolean = false,

    val isGetDataLoading: Boolean = false,
    val isGetDataError: Boolean = false

) : Reducer.ViewState