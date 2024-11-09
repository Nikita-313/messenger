package com.cinetech.ui.screen.personal_area.model

import androidx.compose.ui.graphics.ImageBitmap
import com.cinetech.domain.model.AvatarData
import com.cinetech.domain.model.User
import com.cinetech.ui.base.Reducer

sealed class PersonalAreaUiEvent : Reducer.ViewEvent {
    class UpdateUserData(val user: User) : PersonalAreaUiEvent()
    class UpdateEditUserData(val user: User?) : PersonalAreaUiEvent()
    class UpdateUserAvatarData( val updateAvatarData: AvatarData?) : PersonalAreaUiEvent()
    class UpdateUserImage(val image: ImageBitmap?) : PersonalAreaUiEvent()
    class UpdateUserNewImage(val image: ImageBitmap?) : PersonalAreaUiEvent()
    class UpdateEditMode(val isEnable: Boolean) : PersonalAreaUiEvent()
    class ShowDatePiker(val isShow: Boolean) : PersonalAreaUiEvent()
    class UpdateLoadingNewData(val isLoading: Boolean) : PersonalAreaUiEvent()
    class UpdateLoadingGetData(val isLoading: Boolean) : PersonalAreaUiEvent()
    class UpdateErrorGetData(val isError: Boolean) : PersonalAreaUiEvent()
}