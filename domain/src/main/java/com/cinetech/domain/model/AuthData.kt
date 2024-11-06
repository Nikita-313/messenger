package com.cinetech.domain.model

data class AuthData(
    val refreshToken: String?,
    val accessToken: String?,
    val userId: String?,
    val isUserExists: Boolean?
)
