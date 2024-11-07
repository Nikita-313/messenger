package com.cinetech.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import com.cinetech.data.local.jwt_token.JwtTokenDao
import com.cinetech.data.local.jwt_token.JwtTokenEntity
import com.cinetech.data.local.user.UserDao
import com.cinetech.data.local.user.UserEntity

@Database(
    entities = [
        JwtTokenEntity::class,
        UserEntity::class,
    ],
    version = 1,
    exportSchema = true,
    autoMigrations = [],
)
abstract class AppDatabase : RoomDatabase() {
    abstract fun jwtTokenDao(): JwtTokenDao
    abstract fun userDao(): UserDao
}