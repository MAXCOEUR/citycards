package com.example.citycards.Model

import com.google.gson.annotations.SerializedName
import java.util.Date

data class CityList(
    val currentOffset: Int,
    val data: List<City>,
    val totalCount  : Int)


data class City(
    @SerializedName("idUnique")
    val idUnique: Int?=null,
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

    var favori: Boolean?=null,
    var dateObtention: Date?=null
): java.io.Serializable {
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
