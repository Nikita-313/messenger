package com.cinetech.data.repository

import com.cinetech.data.mapping.toDomain
import com.cinetech.data.remote.AuthService
import com.cinetech.data.remote.model.CheckAuthCodeRequest
import com.cinetech.data.remote.model.SendAuthCodeRequest
import com.cinetech.domain.exeption.NotFoundException
import com.cinetech.domain.exeption.UnknownException
import com.cinetech.domain.exeption.ValidationException
import com.cinetech.domain.model.AuthData
import com.cinetech.domain.repository.AuthRepository
import com.cinetech.domain.utils.Response
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import javax.inject.Inject

class AuthRepositoryImpl @Inject constructor(
    private val authService: AuthService
) : AuthRepository {

    override suspend fun sendAuthCode(phone: String): Flow<Response<out Boolean>> {
        return flow {
            emit(Response.Loading)
            val response = authService.sendAuthCode(SendAuthCodeRequest(phone))

            if(!response.isSuccessful){
                if (response.code() == 422) throw ValidationException(response.message())
                else throw UnknownException(response.message())
            }

            val body = response.body() ?: throw UnknownException(response.message())
            emit(Response.Success(body.isSuccess))
        }.catch { e ->
            emit(
                Response.Error(
                    message = e.message,
                    throwable = e
                )
            )
        }.flowOn(Dispatchers.IO)
    }

    override suspend fun checkAuthCode(phone: String, code: String): Flow<Response<out AuthData>> {
        return flow {
            emit(Response.Loading)
            val response = authService.checkAuthCode(CheckAuthCodeRequest(phone = phone, code = code))

            if(!response.isSuccessful){
                if (response.code() == 422) throw ValidationException(response.message())
                if (response.code() == 404) throw NotFoundException(response.message())
                else throw UnknownException(response.message())
            }

            val body = response.body() ?: throw UnknownException()
            emit(Response.Success(body.toDomain()))
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