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
    val country: String?=null)