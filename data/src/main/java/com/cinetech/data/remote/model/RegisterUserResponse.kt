package com.cinetech.data.remote.model

data class RegisterUserResponse(
    val refresh_token: String?,
    val access_token: String?,
    val user_id: String?,
)
