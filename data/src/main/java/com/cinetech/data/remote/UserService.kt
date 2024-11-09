package com.cinetech.data.remote

import com.cinetech.data.remote.model.GetUserResponse
import com.cinetech.data.remote.model.UpdateUserRequest
import com.cinetech.data.remote.model.UpdateUserResponse
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.PUT

interface UserService {

    @GET("/api/v1/users/me/")
    suspend fun getUser(): Response<GetUserResponse>

    @PUT("/api/v1/users/me/")
    suspend fun updateUser(@Body updateUserRequest: UpdateUserRequest): Response<UpdateUserResponse>
}