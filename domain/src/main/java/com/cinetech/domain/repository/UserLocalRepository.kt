package com.cinetech.domain.repository

import com.cinetech.domain.model.User

interface UserLocalRepository {

    suspend fun save(user: User)

    suspend fun getUser(): User?

    suspend fun delete()
}