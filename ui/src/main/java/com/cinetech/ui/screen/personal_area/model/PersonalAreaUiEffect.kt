package com.cinetech.ui.screen.personal_area.model

import com.cinetech.ui.base.Reducer

sealed class PersonalAreaUiEffect : Reducer.ViewEffect {
    class ShowToast(var rId: Int) : PersonalAreaUiEffect()
}