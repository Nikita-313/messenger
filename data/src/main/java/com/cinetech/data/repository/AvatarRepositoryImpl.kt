package com.cinetech.data.repository

import com.cinetech.data.local.avatar.AvatarDao
import com.cinetech.data.mapping.toDomain
import com.cinetech.data.mapping.toEntity
import com.cinetech.domain.model.AvatarData
import com.cinetech.domain.repository.AvatarRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

class AvatarRepositoryImpl @Inject constructor(
    private val avatarDao: AvatarDao
) : AvatarRepository {
    override suspend fun delete() = withContext(Dispatchers.IO) { avatarDao.delete() }

    override suspend fun getAvatar(): AvatarData? = withContext(Dispatchers.IO) { avatarDao.getAvatar()?.toDomain()  }

    override suspend fun save(avatarData: AvatarData) = withContext(Dispatchers.IO) { avatarDao.upsert(avatarData.toEntity()) }
}