package com.cinetech.domain.repository

import com.cinetech.domain.model.RegisterAuthData
import com.cinetech.domain.model.RegisterUserData
import com.cinetech.domain.utils.Response
import kotlinx.coroutines.flow.Flow

interface RegistrationRepository {
    fun registerUser(registerUserData: RegisterUserData): Flow<Response<out RegisterAuthData>>
}