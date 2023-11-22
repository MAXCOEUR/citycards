package com.example.citycards.dataBase.Dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import com.example.citycards.Model.City

@Dao
interface CityDAO {

    @Insert
    suspend fun insertCity(city: City)

    @Delete
    suspend fun deleteCity(city: City)

    @Query("Select * from City where owner = :idUser")
    suspend fun getAllOfUser(idUser: Int): List<City>


}