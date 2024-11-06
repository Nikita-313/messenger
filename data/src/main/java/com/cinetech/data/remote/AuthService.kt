package com.cinetech.data.remote

import com.cinetech.data.remote.model.SendAuthCodeRequest
import com.cinetech.data.remote.model.CheckAuthCodeRequest
import com.cinetech.data.remote.model.CheckAuthCodeResponse
import com.cinetech.data.remote.model.SendAuthCodeResponse
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.POST

interface AuthService {

    @POST("/api/v1/users/send-auth-code/")
    suspend fun sendAuthCode(@Body phone: SendAuthCodeRequest): Response<SendAuthCodeResponse>

    @POST("/api/v1/users/check-auth-code/")
    suspend fun checkAuthCode(@Body code: CheckAuthCodeRequest): Response<CheckAuthCodeResponse>

}