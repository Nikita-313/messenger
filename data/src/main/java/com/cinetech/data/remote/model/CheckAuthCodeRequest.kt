package com.cinetech.data.remote.model

data class CheckAuthCodeRequest(
    val phone: String,
    val code: String,
)
