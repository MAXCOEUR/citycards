package com.example.citycards.Model

import java.io.Serializable

data class User(
    val id: Int,
    val username: String,
    val email: String,
    val password: String,
    val avatar: String?,
    val jeton: Int
) : Serializable

data class CreateUser(
    var username: String? = null,
    var email: String? = null,
    var password: String? = null,
    var avatar: String? = null
)
data class LoginUser(
    var usernameEmail: String? = null,
    var password: String? = null,
)