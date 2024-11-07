package com.cinetech.data.local.jwt_token

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.cinetech.data.local.jwt_token.JwtTokenDao.Companion.JWT_TOKEN_TABLE_NAME

@Entity(tableName = JWT_TOKEN_TABLE_NAME)
data class JwtTokenEntity(
    @PrimaryKey val id: Int = 1,
    val accessToken: String,
    val refreshToken: String,
)