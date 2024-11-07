package com.cinetech.domain.repository

import com.cinetech.domain.model.LoginAuthData
import com.cinetech.domain.utils.Response
import kotlinx.coroutines.flow.Flow

interface AuthRepository {
    fun sendAuthCode(phone: String): Flow<Response<out Boolean>>
    fun checkAuthCode(phone: String, code: String): Flow<Response<out LoginAuthData>>
}