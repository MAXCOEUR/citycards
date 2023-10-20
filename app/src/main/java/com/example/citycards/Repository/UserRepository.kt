package com.example.citycards.Repository

import com.example.citycards.Model.CreateUser
import com.example.citycards.Model.LoginUser
import com.example.citycards.Model.User
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import retrofit2.Response

object UserRepository {
    suspend fun createUser(createuser: CreateUser) : Flow<User> = flow {
        delay(3000)
        val user = User(
            0,
            createuser.username ?: "", // Utilisez createuser.username s'il n'est pas null, sinon une chaîne vide
            createuser.email ?: "",
            createuser.password ?: "",
            createuser.avatar,
            0
        )

        emit(user)
    }
    suspend fun loginUser(user: LoginUser) : Flow<User> = flow {
        delay(1000)
        val user = User(
            0,
            user.usernameEmail ?: "", // Utilisez createuser.username s'il n'est pas null, sinon une chaîne vide
            "max@outlook.fr",
            user.password ?: "",
            null,
            0
        )

        emit(user)
    }
}