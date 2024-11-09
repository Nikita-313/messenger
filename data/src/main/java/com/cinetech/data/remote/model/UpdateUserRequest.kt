package com.cinetech.data.remote.model

data class UpdateUserRequest(
    val name: String,
    val username: String,
    val birthday: String?,
    val city: String?,
    val vk: String?,
    val instagram: String?,
    val status: String?,
    val avatar: AvatarUpdateApi?,
)
