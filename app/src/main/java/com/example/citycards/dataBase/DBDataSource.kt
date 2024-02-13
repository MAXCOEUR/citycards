package com.example.citycards.dataBase

import android.util.Log
import com.example.citycards.Model.City
import com.example.citycards.Model.User
import com.example.citycards.Repository.UserRepository

object DBDataSource{

    suspend fun insertUser(user: User) {
        try{
            user.token=150
            CityListDataBase.getInstance().UserDAO().insertUser(user)
        }
        catch (e:Exception){
            throw e
        }
    }
    suspend fun getAllCities(user: Int):List<City>{
        return CityListDataBase.getInstance().CityDAO().getAllOfCities(user)
    }

    suspend fun setFavori(City : City){
        return CityListDataBase.getInstance().CityDAO().updateCity(City)
    }

    suspend fun getAllFavoriteCities(user: Int):List<City>{
        return CityListDataBase.getInstance().CityDAO().getAllOfFavoriteCitites(user)
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

    suspend fun updateToken(nb: Int){
        CityListDataBase.getInstance().UserDAO()
    }

    suspend fun getAllRegion(user: Int): List<String>{
        return CityListDataBase.getInstance().CityDAO().getAllRegion(user)
    }

    suspend fun deleteCity(city: City){
        var rang=city.getRang()
        var user = UserRepository.getUserLogin();
        when {
            rang == 1 -> user.token+=20
            rang == 2 -> user.token+=10
            rang == 3 -> user.token+=5
            rang == 4 -> user.token+=3
            rang == 5 -> user.token+=1
        }
        CityListDataBase.getInstance().CityDAO().deleteCity(city)
    }

}
