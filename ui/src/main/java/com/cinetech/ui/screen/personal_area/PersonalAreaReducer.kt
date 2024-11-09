package com.cinetech.ui.screen.personal_area

import com.cinetech.ui.base.Reducer

import com.cinetech.ui.screen.personal_area.model.PersonalAreaUiEffect
import com.cinetech.ui.screen.personal_area.model.PersonalAreaUiEvent
import com.cinetech.ui.screen.personal_area.model.PersonalAreaUiState

class PersonalAreaReducer : Reducer<PersonalAreaUiState, PersonalAreaUiEvent, PersonalAreaUiEffect> {
    override fun reduce(previousState: PersonalAreaUiState, event: PersonalAreaUiEvent): Pair<PersonalAreaUiState, PersonalAreaUiEffect?> {
        return when (event) {
            is PersonalAreaUiEvent.UpdateEditMode -> {
                previousState.copy(isEditModeEnabled = event.isEnable) to null
            }

            is PersonalAreaUiEvent.UpdateUserData -> {
                previousState.copy(user = event.user) to null
            }

            is PersonalAreaUiEvent.ShowDatePiker -> {
                previousState.copy(isShowDatePiker = event.isShow) to null
            }

            is PersonalAreaUiEvent.UpdateEditUserData -> {
                previousState.copy(editUser = event.user) to null
            }

            is PersonalAreaUiEvent.UpdateUserImage -> {
                previousState.copy(userImage = event.image) to null
            }

            is PersonalAreaUiEvent.UpdateLoadingNewData -> {
                previousState.copy(isUpdateDataLoading = event.isLoading) to null
            }

            is PersonalAreaUiEvent.UpdateUserAvatarData -> {
                previousState.copy(updateAvatarData = event.updateAvatarData) to null
            }

            is PersonalAreaUiEvent.UpdateUserNewImage -> {
                previousState.copy(newUserImage = event.image) to null
            }

            is PersonalAreaUiEvent.UpdateLoadingGetData -> {
                previousState.copy(isGetDataLoading = event.isLoading) to null
            }
            is PersonalAreaUiEvent.UpdateErrorGetData -> {
                previousState.copy(isGetDataError = event.isError) to null
            }
        }
    }

}