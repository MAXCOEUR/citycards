package com.example.citycards.dataBase

import com.example.citycards.Model.City
import com.example.citycards.Model.User

object DBDataSource{

    suspend fun insertUser(user: User) {
        CityListDataBase.getInstance().UserDAO().insertUser(user)
    }

    suspend fun getUser(mail: String) :User {
        return CityListDataBase.getInstance().UserDAO().getUserFromMail(mail);
    }

    suspend fun insertCity(city: City){
        CityListDataBase.getInstance().CityDAO().insertCity(city)
    }

    suspend fun updateUser(user: User){
        CityListDataBase.getInstance().UserDAO().updateUsers(user)
    }

}
