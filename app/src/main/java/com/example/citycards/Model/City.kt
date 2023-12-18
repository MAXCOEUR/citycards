package com.example.citycards.Model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName
import java.util.Date
import kotlin.random.Random

data class CityList(
    val currentOffset: Int,
    val data: List<City>,
    val totalCount  : Int)


@Entity(foreignKeys = arrayOf(ForeignKey(entity = User::class,
    parentColumns = arrayOf("id"),
    childColumns = arrayOf("owner"),
    onDelete = ForeignKey.CASCADE)))
data class City(
    @SerializedName("idUnique")
    @PrimaryKey(autoGenerate = true)
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
    var dateObtention: Long= Date().time,
    var owner: Int

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
    companion object {
        fun getPlagePop(nbrRang:Int): Pair<Int, Int> {
            return when {
                nbrRang == 5 -> Pair(0, 50000)
                nbrRang == 4 -> Pair(50000, 100000)
                nbrRang == 3 -> Pair(100000, 180000)
                nbrRang == 2 -> Pair(180000, 500000)
                nbrRang == 1 -> Pair(500000, Int.MAX_VALUE)
                else -> {
                    Pair(0, Int.MAX_VALUE)
                }
            }
        }

        fun getOffset(nbrRang: Int): Int{
            return when {
                nbrRang == 5 -> Random.nextInt(1,520)
                nbrRang == 4 ->  Random.nextInt(1,100)
                nbrRang == 3 ->  Random.nextInt(1,37)
                nbrRang == 2 ->  Random.nextInt(1,14)
                nbrRang == 1 ->  Random.nextInt(1,4)
                else -> {
                    0
                }
            }
        }
    }


}
