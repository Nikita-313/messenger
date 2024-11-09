package com.cinetech.data.mapping

import com.cinetech.data.local.avatar.AvatarEntity
import com.cinetech.data.local.jwt_token.JwtTokenEntity
import com.cinetech.data.local.user.AvatarsEntityUrl
import com.cinetech.data.local.user.UserEntity
import com.cinetech.domain.JwtToken
import com.cinetech.domain.model.AvatarData
import com.cinetech.domain.model.AvatarsUrl
import com.cinetech.domain.model.User

fun JwtToken.toEntity(): JwtTokenEntity {
    return JwtTokenEntity(
        refreshToken = refreshToken,
        accessToken = accessToken,
    )
}

fun JwtTokenEntity.toDomain(): JwtToken {
    return JwtToken(
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

fun AvatarsUrl.toAvatarData(): AvatarsEntityUrl {
    return AvatarsEntityUrl(
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

fun AvatarsEntityUrl.toAvatar(): AvatarsUrl {
    return AvatarsUrl(
        avatar = avatar,
        bigAvatar = bigAvatar,
        miniAvatar = miniAvatar,
    )
}

fun AvatarData.toEntity(): AvatarEntity {
    return AvatarEntity(
        filename = filename,
        base64 = base64
    )
}

fun AvatarEntity.toDomain(): AvatarData {
    return AvatarData(
        filename = filename,
        base64 = base64
    )
}