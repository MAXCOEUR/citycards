package com.example.citycards.Model

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.io.Serializable
@Entity
data class User(
    @PrimaryKey(autoGenerate = true) val id: Int?=null,
    var username: String? = null,
    var email: String? = null,
    var password: String? = null,
    var avatar: String? = null,
    var jeton: Int? = null
) : Serializable
data class LoginUser(
    var usernameEmail: String? = null,
    var password: String? = null,
)