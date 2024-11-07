package com.cinetech.domain.model

data class RegisterAuthData(
    val refreshToken: String?,
    val accessToken: String?,
    val userId: String?,
)
