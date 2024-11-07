package com.cinetech.data.mapping

import com.cinetech.data.local.jwt_token.JwtTokenEntity
import com.cinetech.data.local.user.AvatarData
import com.cinetech.data.local.user.UserEntity
import com.cinetech.domain.JwtToken
import com.cinetech.domain.model.Avatar
import com.cinetech.domain.model.User

fun JwtToken.toEntity(): JwtTokenEntity {
    return JwtTokenEntity(
        refreshToken = refreshToken,
        accessToken = accessToken,
    )
}

fun User.toEntity(): UserEntity {
    return UserEntity(
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
        avatars = avatars?.toAvatarData(),
    )
}

fun Avatar.toAvatarData(): AvatarData {
    return AvatarData(
        avatar = avatar,
        bigAvatar = bigAvatar,
        miniAvatar = miniAvatar,
    )
}

fun UserEntity.toDomain(): User {
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

fun AvatarData.toAvatar(): Avatar {
    return Avatar(
        avatar = avatar,
        bigAvatar = bigAvatar,
        miniAvatar = miniAvatar,
    )
}