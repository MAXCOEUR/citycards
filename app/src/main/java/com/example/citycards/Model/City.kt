package com.example.citycards.Model

import com.google.gson.annotations.SerializedName

data class CityList(
    val currenOfset: Int,
    val data: List<City>,
    val totalCount  : Int)


data class City(
    @SerializedName("id")
    val id : Int?=null,
    @SerializedName("name")
    val name: String?=null,
    @SerializedName("Region")
    val Region : String?=null,
    @SerializedName("Population")
    val Population : Int?=null,
    @SerializedName("Longitude")
    val Longitude : Float?=null,
    @SerializedName("Latitude")
    val Latitude : Float?=null,
    @SerializedName("Country")
    val Country: String?=null)