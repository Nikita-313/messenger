package com.cinetech.domain.model

data class User(
    val userId: Long,
    val name: String,
    val username: String,
    val online: Boolean,
    val phone: String,
    val birthday: String?,
    val city: String?,
    val vk: String?,
    val instagram: String?,
    val status: String?,
    val avatar: String?,
    val last: String?,
    val created: String?,
    val completedTask: Long?,
    val avatars: AvatarsUrl?,
)
