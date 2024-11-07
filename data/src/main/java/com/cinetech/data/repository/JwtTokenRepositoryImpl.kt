package com.cinetech.data.repository

import com.cinetech.data.local.jwt_token.JwtTokenDao
import com.cinetech.data.mapping.toEntity
import com.cinetech.domain.JwtToken
import com.cinetech.domain.repository.JwtTokenRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

class JwtTokenRepositoryImpl @Inject constructor(
    private val jwtTokenDao: JwtTokenDao
) : JwtTokenRepository {

    override suspend fun save(jwt: JwtToken) = withContext(Dispatchers.IO) { jwtTokenDao.upsert(jwt.toEntity()) }

    override suspend fun updateRefreshToken(refreshToken: String) = withContext(Dispatchers.IO) { jwtTokenDao.updateRefreshToken(refreshToken) }

    override suspend fun updateAccessToken(accessToken: String) = withContext(Dispatchers.IO) { jwtTokenDao.updateAccessToken(accessToken) }

    override suspend fun getAccessToken(): String? = withContext(Dispatchers.IO) { jwtTokenDao.getAccessToken() }

    override suspend fun getRefreshToken(): String? = withContext(Dispatchers.IO) { jwtTokenDao.getRefreshToken() }

    override suspend fun delete() = withContext(Dispatchers.IO) { jwtTokenDao.delete() }
}