package com.cinetech.data.mapping

import com.cinetech.data.remote.model.AvatarApi
import com.cinetech.data.remote.model.AvatarUpdateApi
import com.cinetech.data.remote.model.CheckAuthCodeResponse
import com.cinetech.data.remote.model.ProfileDataApi
import com.cinetech.data.remote.model.RegisterUserRequest
import com.cinetech.data.remote.model.RegisterUserResponse
import com.cinetech.data.remote.model.UpdateUserRequest
import com.cinetech.domain.model.AvatarsUrl
import com.cinetech.domain.model.LoginAuthData
import com.cinetech.domain.model.RegisterAuthData
import com.cinetech.domain.model.RegisterUserData
import com.cinetech.domain.model.AvatarData
import com.cinetech.domain.model.UpdateUserData
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

fun ProfileDataApi.toDomain(): User {
    return User(
        userId = id,
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

fun AvatarApi.toAvatar(): AvatarsUrl {
    return AvatarsUrl(
        avatar = avatar,
        bigAvatar = bigAvatar,
        miniAvatar = miniAvatar,
    )
}

fun UpdateUserData.toRequest(): UpdateUserRequest {
    return UpdateUserRequest(
        name = name,
        username = username,
        birthday = birthday,
        city = city,
        vk = vk,
        instagram = instagram,
        status = status,
        avatar = avatar?.toApi(),
    );
}

fun AvatarData.toApi(): AvatarUpdateApi {
    return AvatarUpdateApi(
        filename = filename,
        base_64 = base64,
    )
}