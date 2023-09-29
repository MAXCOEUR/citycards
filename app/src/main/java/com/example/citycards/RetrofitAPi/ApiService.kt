package com.example.citycards.RetrofitAPi

import com.example.citycards.Model.User
import com.example.citycards.Model.City
import com.example.citycards.Model.CityList
import retrofit2.Response
import retrofit2.http.*

interface ApiService {

    @GET("CityList")
    suspend fun getCities(): Response<CityList>


}