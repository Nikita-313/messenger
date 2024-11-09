package com.cinetech.data.local.avatar

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.cinetech.data.local.avatar.AvatarDao.Companion.AVATAR_TABLE_NAME

@Entity(tableName = AVATAR_TABLE_NAME)
data class AvatarEntity(
    @PrimaryKey val id: Int = 1,
    val filename: String,
    val base64: String,
)