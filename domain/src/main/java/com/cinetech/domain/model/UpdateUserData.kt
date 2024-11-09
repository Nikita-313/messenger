package com.cinetech.domain.model

data class UpdateUserData(
    val name: String,
    val username: String,
    val birthday: String?,
    val city: String?,
    val vk: String?,
    val instagram: String?,
    val status: String?,
    val avatar: AvatarData?,
)
