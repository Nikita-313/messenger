package com.cinetech.data.remote

import com.cinetech.data.remote.model.RefreshTokenRequest
import com.cinetech.data.remote.model.RefreshTokenResponse
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.POST

interface RefreshJwtService {

    @POST("/api/v1/users/refresh-token/")
    suspend fun refreshAccessToken(@Body refreshTokenRequest: RefreshTokenRequest): Response<RefreshTokenResponse>
}