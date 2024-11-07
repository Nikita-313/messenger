package com.cinetech.data.local.jwt_token

import androidx.room.Dao
import androidx.room.Query
import androidx.room.Upsert

@Dao
interface JwtTokenDao {

    @Upsert
    suspend fun upsert(jwtTokenEntity: JwtTokenEntity)

    @Query("UPDATE $JWT_TOKEN_TABLE_NAME SET refreshToken = :refreshToken WHERE id = 1")
    suspend fun updateRefreshToken(refreshToken:String)

    @Query("UPDATE $JWT_TOKEN_TABLE_NAME SET accessToken = :accessToken WHERE id = 1")
    suspend fun updateAccessToken(accessToken:String)

    @Query("SELECT accessToken FROM $JWT_TOKEN_TABLE_NAME WHERE id = 1")
    suspend fun getAccessToken(): String?

    @Query("SELECT refreshToken FROM $JWT_TOKEN_TABLE_NAME WHERE id = 1")
    suspend fun getRefreshToken(): String?


    @Query("DELETE FROM $JWT_TOKEN_TABLE_NAME WHERE id = 1")
    suspend fun delete()

    companion object {
        const val JWT_TOKEN_TABLE_NAME = "jwt_token"
    }
}