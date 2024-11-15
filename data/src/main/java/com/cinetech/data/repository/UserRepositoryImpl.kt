package com.cinetech.data.repository

import com.cinetech.data.mapping.toAvatar
import com.cinetech.data.mapping.toDomain
import com.cinetech.data.mapping.toRequest
import com.cinetech.data.remote.UserService
import com.cinetech.domain.exeption.UnknownException
import com.cinetech.domain.exeption.ValidationException
import com.cinetech.domain.model.AvatarsUrl
import com.cinetech.domain.model.UpdateUserData
import com.cinetech.domain.model.User
import com.cinetech.domain.repository.UserRepository
import com.cinetech.domain.utils.Response
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import javax.inject.Inject

class UserRepositoryImpl @Inject constructor(
    private val userService: UserService
) : UserRepository {

    override fun getUser(): Flow<Response<out User>> {
        return flow {
            emit(Response.Loading)
            val response = userService.getUser()
            if (!response.isSuccessful) throw UnknownException(response.message())
            val body = response.body() ?: throw UnknownException()
            emit(Response.Success(body.profile_data.toDomain()))
        }.catch { e ->
            emit(
                Response.Error(
                    message = e.message,
                    throwable = e
                )
            )
        }.flowOn(Dispatchers.IO)
    }

    override fun updateUser(updateUserData: UpdateUserData): Flow<Response<out AvatarsUrl?>> {
        return flow {
            emit(Response.Loading)
            val response = userService.updateUser(updateUserData.toRequest())
            if (!response.isSuccessful) {
                if(response.code() == 422) throw ValidationException(response.message())
                throw UnknownException(response.message())
            }
            val body = response.body() ?: throw UnknownException()
            emit(Response.Success(body.avatars?.toAvatar()))
        }.catch { e ->
            emit(
                Response.Error(
                    message = e.message,
                    throwable = e
                )
            )
        }.flowOn(Dispatchers.IO)
    }
}