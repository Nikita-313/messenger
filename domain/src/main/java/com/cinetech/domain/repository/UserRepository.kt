package com.cinetech.domain.repository

import com.cinetech.domain.model.AvatarsUrl
import com.cinetech.domain.model.UpdateUserData
import com.cinetech.domain.model.User
import com.cinetech.domain.utils.Response
import kotlinx.coroutines.flow.Flow

interface UserRepository {
    fun getUser(): Flow<Response<out User>>
    fun updateUser(updateUserData: UpdateUserData): Flow<Response<out AvatarsUrl?>>
}