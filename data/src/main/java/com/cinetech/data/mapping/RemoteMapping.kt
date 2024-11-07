package com.cinetech.data.mapping

import com.cinetech.data.remote.model.CheckAuthCodeResponse
import com.cinetech.data.remote.model.RegisterUserRequest
import com.cinetech.data.remote.model.RegisterUserResponse
import com.cinetech.domain.model.LoginAuthData
import com.cinetech.domain.model.RegisterAuthData
import com.cinetech.domain.model.RegisterUserData

fun CheckAuthCodeResponse.toDomain(): LoginAuthData {
    return LoginAuthData(
        refreshToken = refresh_token,
        accessToken = access_token,
        userId = user_id,
        isUserExists = is_user_exists,
    )
}

fun RegisterUserData.toRequest(): RegisterUserRequest {
    return RegisterUserRequest(
        phone = phone,
        name = name,
        username = userName,
    )
}

fun RegisterUserResponse.toDomain(): RegisterAuthData {
    return RegisterAuthData(
        refreshToken = refresh_token,
        accessToken = access_token,
        userId = user_id
    )
}