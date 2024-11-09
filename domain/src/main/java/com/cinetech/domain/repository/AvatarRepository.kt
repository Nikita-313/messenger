package com.cinetech.domain.repository

import com.cinetech.domain.model.AvatarData

interface AvatarRepository {
    suspend fun delete()
    suspend fun getAvatar(): AvatarData?
    suspend fun save(avatarData: AvatarData)
}