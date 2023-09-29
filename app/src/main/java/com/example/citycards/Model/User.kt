package com.example.citycards.Model

data class User(val id: Int, val username: String, val email:String, val password: String, val avatar: String, var Jeton: Int)

data class CreateUser(
    var username: String? = null,
    var email: String? = null,
    var password: String? = null,
    var avatar: String? = null
)