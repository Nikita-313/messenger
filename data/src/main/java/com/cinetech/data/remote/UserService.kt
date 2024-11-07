package com.cinetech.data.remote

import com.cinetech.data.remote.model.GetUserResponse
import retrofit2.Response
import retrofit2.http.GET

interface UserService {

    @GET("/api/v1/users/me/")
    suspend fun getUser(): Response<GetUserResponse>

}