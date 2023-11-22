package com.example.citycards.dataBase

import android.content.Context
import androidx.room.AutoMigration
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.citycards.Model.City
import com.example.citycards.Model.User
import com.example.citycards.dataBase.Dao.CityDAO
import com.example.citycards.dataBase.Dao.UserDAO

@Database(entities = [User::class,City::class], version = 1
)
internal abstract class CityListDataBase : RoomDatabase() {

    abstract fun UserDAO(): UserDAO
    abstract fun CityDAO(): CityDAO

    companion object {

        private lateinit var instance: CityListDataBase
        fun initDatabase(context: Context) {
            instance = Room.databaseBuilder(
                context, CityListDataBase::class.java,
                "city-db"
            ).build()
        }


        fun getInstance(): CityListDataBase {
            return instance
        }
    }
}