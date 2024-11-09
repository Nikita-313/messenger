package com.cinetech.domain.repository

import com.cinetech.domain.JwtToken

interface JwtTokenRepository {

    suspend fun save(jwt: JwtToken)

    suspend fun getToken():JwtToken?

    suspend fun updateRefreshToken(refreshToken:String)

    suspend fun updateAccessToken(accessToken:String)

    suspend fun getAccessToken(): String?

    suspend fun getRefreshToken(): String?

    suspend fun delete()
}