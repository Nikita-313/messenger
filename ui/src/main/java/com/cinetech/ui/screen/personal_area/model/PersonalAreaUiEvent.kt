package com.cinetech.ui.screen.personal_area.model

import androidx.compose.ui.graphics.ImageBitmap
import com.cinetech.domain.model.User
import com.cinetech.ui.base.Reducer

sealed class PersonalAreaUiEvent : Reducer.ViewEvent {
    data class UpdateUserData(val user: User) : PersonalAreaUiEvent()
    data class UpdateEditUserData(val user: User?) : PersonalAreaUiEvent()
    data class UpdateEditUserImage(val image: ImageBitmap?) : PersonalAreaUiEvent()
    data class UpdateUserImage(val image: ImageBitmap?) : PersonalAreaUiEvent()
    class UpdateLoading(val isLoading: Boolean) : PersonalAreaUiEvent()
    class UpdateEditMode(val isEnable: Boolean) : PersonalAreaUiEvent()
    class ShowDatePiker(val isShow: Boolean) : PersonalAreaUiEvent()
}