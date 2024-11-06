package com.cinetech.ui.navigation

import kotlinx.serialization.Serializable

sealed interface Screen {

    @Serializable
    data object AuthGraph : Screen

    @Serializable
    data object Auth : Screen

    @Serializable
    data object SelectCountryCode : Screen
}