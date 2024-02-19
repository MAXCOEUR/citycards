package com.example.citycards.Model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName
import java.util.Date
import kotlin.random.Random
data class Metadata(
    val currentOffset: Int,
    val totalCount  : Int)
data class CityList(
    val data: List<City>,
    val metadata:Metadata)


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
        val PETITE_VILLE:Pair<Int,Int> = Pair(0, 50000)
        val MOYENNE_VILLE:Pair<Int,Int> = Pair(50000, 100000)
        val GRANDE_VILLE:Pair<Int,Int> = Pair(100000, 180000)
        val METROPOLE:Pair<Int,Int> = Pair(180000, 500000)
        val MEGAPOL:Pair<Int,Int> = Pair(500000, Int.MAX_VALUE)
        fun getPlagePop(nbrRang:Int): Pair<Int, Int> {
            return when {
                nbrRang == 5 -> PETITE_VILLE
                nbrRang == 4 -> MOYENNE_VILLE
                nbrRang == 3 -> GRANDE_VILLE
                nbrRang == 2 -> METROPOLE
                nbrRang == 1 -> MEGAPOL
                else -> {
                    Pair(0, Int.MAX_VALUE)
                }
            }
        }
        var NBR_PETITE_VILLE:Int = 519
        var NBR_MOYENNE_VILLE:Int = 99
        var NBR_GRANDE_VILLE:Int = 36
        var NBR_METROPOLE:Int = 13
        var NBR_MEGAPOL:Int = 3
        fun getOffset(nbrRang: Int): Int{
            return when {
                nbrRang == 5 -> Random.nextInt(1,NBR_PETITE_VILLE+1)
                nbrRang == 4 ->  Random.nextInt(1,NBR_MOYENNE_VILLE+1)
                nbrRang == 3 ->  Random.nextInt(1,NBR_GRANDE_VILLE+1)
                nbrRang == 2 ->  Random.nextInt(1,NBR_METROPOLE+1)
                nbrRang == 1 ->  Random.nextInt(1,NBR_MEGAPOL+1)
                else -> {
                    0
                }
            }
        }
    }


}
