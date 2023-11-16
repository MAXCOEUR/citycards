package com.example.citycards.Model

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.io.Serializable
@Entity
data class User(
    @PrimaryKey(autoGenerate = true) val id: Int,
    val username: String,
    val email: String,
    val password: String,
    val avatar: String?,
    var jeton: Int
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