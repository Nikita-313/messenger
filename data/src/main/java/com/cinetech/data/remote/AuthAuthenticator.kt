package com.cinetech.data.remote

import com.cinetech.data.remote.model.RefreshTokenRequest
import com.cinetech.domain.JwtToken
import com.cinetech.domain.repository.JwtTokenRepository
import kotlinx.coroutines.runBlocking
import okhttp3.Authenticator
import okhttp3.Request
import okhttp3.Response
import okhttp3.Route
import javax.inject.Inject

class AuthAuthenticator @Inject constructor(
    private val jwtTokenRepository: JwtTokenRepository,
    private val refreshJwtService: RefreshJwtService
) : Authenticator {

    override fun authenticate(route: Route?, response: Response): Request? {
        val currentToken = runBlocking { jwtTokenRepository.getAccessToken() }

        synchronized(this) {

            val updatedToken = runBlocking { jwtTokenRepository.getAccessToken() }

            val token = if (currentToken != updatedToken) {
                updatedToken
            } else {
                val refreshToken = runBlocking { jwtTokenRepository.getRefreshToken() }

                if (refreshToken != null) {
                    val responseJwt = runBlocking { refreshJwtService.refreshAccessToken(RefreshTokenRequest(refreshToken)) }
                    if (responseJwt.isSuccessful && responseJwt.body()?.access_token != null) {
                        val newtToken = responseJwt.body()
                        if (newtToken?.access_token != null && newtToken.refresh_token != null) {
                            runBlocking { jwtTokenRepository.save(JwtToken(accessToken = newtToken.access_token, refreshToken = newtToken.refresh_token)) }
                            newtToken.access_token
                        } else null
                    } else null
                } else null
            }

            if (token == null) return null
            return response.request().newBuilder()
                .header(HEADER_AUTHORIZATION, "$TOKEN_TYPE $token")
                .build()
        }
    }

    companion object {
        const val HEADER_AUTHORIZATION = "Authorization"
        const val TOKEN_TYPE = "Bearer"
    }

}