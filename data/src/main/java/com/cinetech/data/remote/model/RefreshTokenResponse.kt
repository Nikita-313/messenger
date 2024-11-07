package com.cinetech.data.remote.model

data class RefreshTokenResponse(
    val refresh_token: String?,
    val access_token: String?,
    val user_id: String?,
)