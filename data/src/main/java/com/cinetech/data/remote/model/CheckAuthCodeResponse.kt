package com.cinetech.data.remote.model

data class CheckAuthCodeResponse(
    val refreshToken: String?,
    val accessToken: String?,
    val userId: String?,
    val isUserExists: Boolean?
)