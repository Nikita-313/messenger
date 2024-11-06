package com.cinetech.data.mapping

import com.cinetech.data.remote.model.CheckAuthCodeResponse
import com.cinetech.domain.model.AuthData

fun CheckAuthCodeResponse.toDomain(): AuthData {
    return AuthData(
        refreshToken = refreshToken,
        accessToken = accessToken,
        userId = userId,
        isUserExists = isUserExists,
    )
}