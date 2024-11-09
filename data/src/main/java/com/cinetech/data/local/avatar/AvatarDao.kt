package com.cinetech.data.local.avatar

import androidx.room.Dao
import androidx.room.Query
import androidx.room.Upsert

@Dao
interface AvatarDao {

    @Upsert
    suspend fun upsert(avatarEntity: AvatarEntity)

    @Query("SELECT * FROM $AVATAR_TABLE_NAME WHERE id = 1")
    suspend fun getAvatar(): AvatarEntity?


    @Query("DELETE FROM $AVATAR_TABLE_NAME WHERE id = 1")
    suspend fun delete()

    companion object {
        const val AVATAR_TABLE_NAME = "avatar_table"
    }

}