package com.example.citycards.Repository

import com.example.citycards.Model.LoginUser
import com.example.citycards.Model.User
import com.example.citycards.dataBase.DBDataSource
import com.example.citycards.dataSource.CacheDataSource
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

object UserRepository {
    suspend fun createUser(createuser: User) : Flow<User> = flow {
        DBDataSource.insertUser(createuser);
        val user = DBDataSource.getUser(createuser.email)
        emit(user)
    }
    suspend fun loginUser(loginUser: LoginUser) : Flow<User> = flow {
        val user = DBDataSource.getUser(loginUser.userEmail)

        if(user!=null && loginUser.password==user.password){
            emit(user)
        }
        else{
            emit(User(username = "", email = ""))
        }
    }
    fun getUserLogin():User{
        return CacheDataSource.getInstance().getUserLogin();
    }
    fun setUserLogin(user:User){
        CacheDataSource.getInstance().setUserLogin(user)
    }
}