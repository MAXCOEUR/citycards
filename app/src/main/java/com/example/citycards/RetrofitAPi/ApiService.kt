package com.example.citycards.RetrofitAPi

import com.example.citycards.Model.User
import com.example.citycards.Model.City
import com.example.citycards.Model.CityList
import retrofit2.Response
import retrofit2.http.*

interface ApiService {

    @GET("cities/?types=CITY&countryIds=Q142&sort=-population")
    suspend fun getCities(@Query("limit")  limiteur: Int=10,
                          @Query("namePrefix") namePrefix: String="",
                          @Query("offset") offset :Int=0,
                          @Query("minPopulation") minPop: Int=0,
                          @Query("maxPopulation") maxPop: Int?=null,
    ): Response<CityList>
}


