package com.cinetech.data.remote

import com.cinetech.data.remote.model.RegisterUserRequest
import com.cinetech.data.remote.model.RegisterUserResponse
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.POST

interface RegistrationService {
    @POST("/api/v1/users/register/")
    suspend fun registerUser(@Body phone: RegisterUserRequest): Response<RegisterUserResponse>
}