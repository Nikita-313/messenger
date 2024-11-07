package com.cinetech.data.remote.model

data class RegisterUserRequest(
    val phone:String,
    val name: String,
    val username:String,
)