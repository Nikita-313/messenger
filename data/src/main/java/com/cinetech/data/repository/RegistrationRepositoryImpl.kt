package com.cinetech.data.repository

import com.cinetech.data.mapping.toDomain
import com.cinetech.data.mapping.toRequest
import com.cinetech.data.remote.RegistrationService
import com.cinetech.domain.exeption.BadRequestException
import com.cinetech.domain.exeption.UnknownException
import com.cinetech.domain.exeption.ValidationException
import com.cinetech.domain.model.RegisterAuthData
import com.cinetech.domain.model.RegisterUserData
import com.cinetech.domain.repository.RegistrationRepository
import com.cinetech.domain.utils.Response
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import javax.inject.Inject

class RegistrationRepositoryImpl @Inject constructor(
    private val registrationService: RegistrationService
) : RegistrationRepository {
    override fun registerUser(registerUserData: RegisterUserData): Flow<Response<out RegisterAuthData>> {
        return flow {
            emit(Response.Loading)
            val response = registrationService.registerUser(registerUserData.toRequest())

            if (!response.isSuccessful) {
                if (response.code() == 422) throw ValidationException(response.message())
                if (response.code() == 400) throw BadRequestException(response.message())
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