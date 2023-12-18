package com.example.citycards.Repository

import com.example.citycards.Model.City
import com.example.citycards.Model.CityList
import com.example.citycards.Model.QueryDataCity
import com.example.citycards.RetrofitAPi.ApiClient
import com.example.citycards.dataBase.DBDataSource
import com.example.citycards.dataSource.CacheDataSource
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import retrofit2.Response
import java.util.Date

object CityRepository {
    suspend fun getCitysSearch(dataQuery: QueryDataCity): Flow<Response<CityList>> = flow {
        val Api = ApiClient.getApiService
        var response = Api.getCities(
            dataQuery.limiteur,
            dataQuery.namePrefix,
            dataQuery.offset,
            dataQuery.minPop,
            dataQuery.maxPop
        )
        emit(response)
    }

    suspend fun getCityRandom(limit: Int, offset: Int, minPop: Int, maxPop: Int): Flow<Response<CityList>> =
        flow {
            val Api = ApiClient.getApiService
            val response = Api.getCities(limiteur= limit,offset = offset, minPop = minPop, maxPop = maxPop)
            emit(response)
        }

    suspend fun addCity(city: City) {
        DBDataSource.insertCity(city)
    }

    suspend fun getCityCollection(
        dataQuery: QueryDataCity,
    ): Flow<Response<CityList>> = flow {
        var user= CacheDataSource.getInstance().getUserLogin()
        val cityList = user.id?.let { DBDataSource.getAllCities(it) }
        var filteredCity =
            cityList?.filter { it.name?.lowercase()?.contains(dataQuery.namePrefix.lowercase()) ?: true }
        if (dataQuery.region!=null && dataQuery.region != "Toutes les régions") {
            if (filteredCity != null) {
                filteredCity = filteredCity.filter { it.region == dataQuery.region }
            }
        }
        if (dataQuery.rang!=null && dataQuery.rang != 6) {
            if (filteredCity != null) {
                filteredCity = filteredCity.filter { it.getRang() == dataQuery.rang }
            }
        }
        val response =
            Response.success(filteredCity?.let { CityList(data = it, currentOffset = 0, totalCount = 0) })
        emit(response)
    }

    suspend fun getCityCollection_favori(
        dataQuery: QueryDataCity,
    ): Flow<Response<CityList>> = flow {
        var user= CacheDataSource.getInstance().getUserLogin()
        val cityList = user.id?.let { DBDataSource.getAllFavoriteCities(it) }
        var filteredCity =
            cityList?.filter { it.name?.lowercase()?.contains(dataQuery.namePrefix.lowercase()) ?: true }
        if (dataQuery.region != "Toutes les régions") {
            if (filteredCity != null) {
                filteredCity = filteredCity.filter { it.region == dataQuery.region }
            }
        }
        val response =
            Response.success(filteredCity?.let { CityList(data = it, currentOffset = 0, totalCount = 0) })
        emit(response)
    }

    suspend fun getRegion(): Flow<Response<List<String>>> = flow {
        var user= CacheDataSource.getInstance().getUserLogin()
        var regionList :List<String> = listOf("Toutes les régions")
        if(user.id != null){
            regionList += DBDataSource.getAllRegion(user.id!!)
        }
        val response = Response.success(regionList)
        emit(response)
    }

    suspend fun setFavori(City: City): Flow<City> = flow {
        var user= CacheDataSource.getInstance().getUserLogin()
        val response = Response.success(DBDataSource.setFavori(City))
        emit(City)
    }

    suspend fun getRank(): Flow<Response<List<String>>> = flow {
        val regionList = listOf("Tous les rangs","Petite ville", "Moyenne ville", "Grande ville", "Métropole", "Mégapole")
        val response = Response.success(regionList)
        emit(response)
    }

    suspend fun deleteCity(City: City): Flow<City> = flow {
        DBDataSource.deleteCity(City)
        emit(City)
    }
}