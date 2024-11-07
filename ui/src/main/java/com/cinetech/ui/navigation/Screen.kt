package com.cinetech.ui.navigation

import kotlinx.serialization.Serializable

sealed interface Screen {

    @Serializable
    data object AuthGraph : Screen

    @Serializable
    data object Auth : Screen

    @Serializable
    data object SelectCountryCode : Screen

    @Serializable
    data class SmsVerification(val phone: String) : Screen

    @Serializable
    data class Registration(val phone: String) : Screen

    @Serializable
    data object Main: Screen

    @Serializable
    data object Chat: Screen
}