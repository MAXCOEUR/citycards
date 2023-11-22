package com.example.citycards.dataSource

import com.example.citycards.Model.User

class CacheDataSource private constructor() {
    private var user: User = User(username = "", email = "")

    // Méthode pour définir l'utilisateur
    fun setUser(user: User) {
        this.user = user
    }

    // Méthode pour récupérer l'utilisateur
    fun getUser(): User {
        return user
    }

    // Singleton
    companion object {
        @Volatile
        private var instance: CacheDataSource? = null

        fun getInstance(): CacheDataSource {
            return instance ?: synchronized(this) {
                instance ?: CacheDataSource().also { instance = it }
            }
        }
    }
}