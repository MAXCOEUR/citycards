package com.example.citycards.Model

import com.google.gson.annotations.SerializedName

data class CityList(
    val currenOfset: Int,
    val data: List<City>,
    val totalCount  : Int)


data class City(
    @SerializedName("id")
    val id: Int?=null,
    @SerializedName("name")
    val name: String?=null,
    @SerializedName("region")
    val region: String?=null,
    @SerializedName("population")
    val population: Int?=null,
    @SerializedName("longitude")
    val longitude: Float? =null,
    @SerializedName("latitude")
    val latitude: Float?=null,
    @SerializedName("country")
    val country: String?=null,

    val favori: Boolean?=null
){
    fun getRang(): Int {
        return when {
            population == null -> 0
            population < 50000 -> 5
            population < 100000 -> 4
            population < 180000 -> 3
            population < 500000 -> 2
            else -> 1
        }
    }

}