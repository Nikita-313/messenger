package com.cinetech.domain

data class JwtToken(
    val accessToken: String,
    val refreshToken: String,
)
