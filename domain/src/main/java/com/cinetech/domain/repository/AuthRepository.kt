package com.cinetech.domain.repository

import com.cinetech.domain.model.AuthData
import com.cinetech.domain.utils.Response
import kotlinx.coroutines.flow.Flow

interface AuthRepository {
    suspend fun sendAuthCode(phone:String): Flow<Response<out Boolean>>
    suspend fun checkAuthCode(phone: String, code:String): Flow<Response<out AuthData>>
}