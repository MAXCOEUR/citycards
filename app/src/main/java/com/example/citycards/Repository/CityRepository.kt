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
        val response = Api.getCities(
            dataQuery.limiteur,
            dataQuery.namePrefix,
            dataQuery.offset,
            dataQuery.minPop,
            dataQuery.maxPop
        )
        emit(response)
    }

    suspend fun getCityRandom(offet: Int, minPop: Int, maxPop: Int): Flow<Response<CityList>> =
        flow {
            val Api = ApiClient.getApiService
            val response = Api.getCities(offset = offet, minPop = minPop, maxPop = maxPop)
            emit(response)
        }

    suspend fun getCityCollection(
        dataQuery: QueryDataCity,
    ): Flow<Response<CityList>> = flow {
        val cityList = listOf(
            City(1, "New York", "New York", 8537673, -74.006F, 40.7128F, "USA", false),
            City(2, "Los Angeles", "California", 39776830, -118.2437F, 34.0522F, "USA", true),
            City(3, "Paris", "Île-de-France", 2140526, 2.3522F, 48.8566F, "France", false),
            City(4, "London", "Greater London", 8982256, -0.1276F, 51.5072F, "UK", false),
            City(5, "Tokyo", "Kanto", 37435191, 139.6917F, 35.6895F, "Japan", false),
            City(6, "Sydney", "New South Wales", 5312163, 151.2093F, -33.8688F, "Australia", false),
            City(7, "Toronto", "Ontario", 2731571, -79.3832F, 43.6511F, "Canada", false),
            City(8, "Dubai", "Dubai", 3137000, 55.2708F, 25.276987F, "UAE", false),
            City(9, "Mumbai", "Maharashtra", 12442373, 72.8777F, 19.0760F, "India", false),
            City(10, "Rio de Janeiro", "Rio de Janeiro", 12, -43.1729F, -22.9068F, "Brazil", true)
        )
        val response =
            Response.success(CityList(data = cityList, currentOffset = 0, totalCount = 0))
        emit(response)
    }

    suspend fun getCityCollection_favori(
        dataQuery: QueryDataCity,
    ): Flow<Response<CityList>> = flow {
        val cityList = listOf(
            City(2, "Los Angeles", "California", 39776830, -118.2437F, 34.0522F, "USA", true),
            City(10, "Rio de Janeiro", "Rio de Janeiro", 12, -43.1729F, -22.9068F, "Brazil", true)
        )
        val response =
            Response.success(CityList(data = cityList, currentOffset = 0, totalCount = 0))
        emit(response)
    }

    suspend fun getRegion(): Flow<Response<List<String>>> = flow {
        val regionList = listOf("Toutes les régions","California", "Rio de Janeiro", "New York", "Île-de-France", "Greater London", "Kanto", "New South Wales", "Ontario", "Dubai", "Maharashtra")
        val response = Response.success(regionList)
        emit(response)
    }

    suspend fun getRank(): Flow<Response<List<String>>> = flow {
        val regionList = listOf("Tous les rangs","Petite ville", "Moyenne ville", "Grande ville", "Métropole", "Mégapole")
        val response = Response.success(regionList)
        emit(response)
    }
}