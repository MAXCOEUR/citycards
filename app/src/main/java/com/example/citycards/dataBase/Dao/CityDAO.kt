package com.example.citycards.dataBase.Dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import com.example.citycards.Model.City

@Dao
interface CityDAO {

    @Insert
    suspend fun insertCity(city: City)

    @Update
    suspend fun updateCity(city: City)

    @Delete
    suspend fun deleteCity(city: City)

    @Query("Select * from City where owner=:idUser order by idUnique desc")
    suspend fun getAllOfCities(idUser: Int): List<City>

    @Query("Select * from City where favori=1 and owner=:idUser ")
    suspend fun getAllOfFavoriteCitites(idUser: Int): List<City>

    @Query("Select distinct(region) from City where owner=:idUser")
    suspend fun getAllRegion(idUser: Int): List<String>

    //Faire un select unique



}