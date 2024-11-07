package com.cinetech.data.local.user

import androidx.room.Dao
import androidx.room.Query
import androidx.room.Upsert

@Dao
interface UserDao {

    @Upsert
    suspend fun upsert(userEntity: UserEntity)

    @Query("SELECT * FROM $USER_TABLE_NAME WHERE id = 1")
    suspend fun getUser(): UserEntity?

    @Query("DELETE FROM $USER_TABLE_NAME WHERE id = 1")
    suspend fun delete()

    companion object {
        const val USER_TABLE_NAME = "user"
    }
}