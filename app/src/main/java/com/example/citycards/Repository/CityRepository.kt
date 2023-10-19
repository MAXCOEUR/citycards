package com.example.citycards.Repository

import com.example.citycards.Model.City
import com.example.citycards.Model.CityList
import com.example.citycards.Model.QueryDataCity
import com.example.citycards.RetrofitAPi.ApiClient
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import retrofit2.Response

object CityRepository {
    suspend fun getCitysSearch(dataQuery: QueryDataCity): Flow<Response<CityList>> = flow {
        val Api = ApiClient.getApiService
        val response = Api.getCities(dataQuery.limiteur,dataQuery.namePrefix,dataQuery.offset,dataQuery.minPop,dataQuery.maxPop)
        emit(response)
    }
    suspend fun getCityRandom(offet:Int,minPop:Int, maxPop:Int): Flow<Response<CityList>> = flow {
        val Api = ApiClient.getApiService
        val response = Api.getCities(offset = offet,minPop = minPop, maxPop = maxPop)
        emit(response)
    }
    suspend fun getCityCollection(dataQuery: QueryDataCity): Flow<Response<CityList>> = flow {
        val cityList = CityList(
            currenOfset = 0, // Vous pouvez ajuster la valeur de currenOfset selon vos besoins
            data = listOf(
                City(id = 1, name = "Ville1", region = "Région1", population = 100000, longitude = 50.10f, latitude = 34.0f, country = "Pays1"),
                City(id = 2, name = "Ville2", region = "Région2", population = 150000, longitude = 47.890f, latitude = 36.789f, country = "Pays2"),
                City(id = 3, name = "Ville3", region = "Région3", population = 120000, longitude = 46.456f, latitude = 35.678f, country = "Pays3")
            ),
            totalCount = 3 // Le nombre total de villes dans la liste
        )
        val response = Response.success(cityList)
        emit(response)
    }
}