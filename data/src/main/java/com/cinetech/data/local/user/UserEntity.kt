package com.cinetech.data.local.user

import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.cinetech.data.local.user.UserDao.Companion.USER_TABLE_NAME

@Entity(tableName = USER_TABLE_NAME)
data class UserEntity(
    @PrimaryKey val id: Int = 1,
    val userId: Long,
    val name: String,
    val username: String,
    val birthday: String?,
    val city: String?,
    val vk: String?,
    val instagram: String?,
    val status: String?,
    val avatar: String?,
    val last: String?,
    val online: Boolean,
    val created: String?,
    val phone: String,
    val completedTask: Long?,
    @Embedded(prefix = "avatar_")
    val avatars: AvatarData?,
)
