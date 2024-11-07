package com.cinetech.data.remote.model

data class CheckAuthCodeResponse(
    val refresh_token: String?,
    val access_token: String?,
    val user_id: String?,
    val is_user_exists: Boolean
)