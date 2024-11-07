package com.cinetech.data.repository

import com.cinetech.data.local.user.UserDao
import com.cinetech.data.mapping.toDomain
import com.cinetech.data.mapping.toEntity
import com.cinetech.domain.model.User
import com.cinetech.domain.repository.UserLocalRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

class UserLocalRepositoryImpl @Inject constructor(
    private val userDao: UserDao
) : UserLocalRepository {
    override suspend fun save(user: User) = withContext(Dispatchers.IO) { userDao.upsert(user.toEntity()) }

    override suspend fun getUser(): User? = withContext(Dispatchers.IO) { userDao.getUser()?.toDomain() }

    override suspend fun delete() = withContext(Dispatchers.IO) { userDao.delete() }
}