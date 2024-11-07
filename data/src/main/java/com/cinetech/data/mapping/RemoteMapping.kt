package com.cinetech.data.mapping

import com.cinetech.data.remote.model.AvatarResponse
import com.cinetech.data.remote.model.CheckAuthCodeResponse
import com.cinetech.data.remote.model.GetUserResponse
import com.cinetech.data.remote.model.RegisterUserRequest
import com.cinetech.data.remote.model.RegisterUserResponse
import com.cinetech.domain.model.Avatar
import com.cinetech.domain.model.LoginAuthData
import com.cinetech.domain.model.RegisterAuthData
import com.cinetech.domain.model.RegisterUserData
import com.cinetech.domain.model.User

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

fun GetUserResponse.toDomain(): User {
    return User(
        userId = userId,
        name = name,
        username = username,
        birthday = birthday,
        city = city,
        vk = vk,
        instagram = instagram,
        status = status,
        avatar = avatar,
        last = last,
        online = online,
        created = created,
        phone = phone,
        completedTask = completedTask,
        avatars = avatars?.toAvatar(),
    )
}
fun AvatarResponse.toAvatar(): Avatar {
    return Avatar(
        avatar = avatar,
        bigAvatar = bigAvatar,
        miniAvatar = miniAvatar,
    )
}
