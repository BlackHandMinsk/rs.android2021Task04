package com.example.CarDatabaseApp.room

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(entities = [Car::class],version = 2, exportSchema = false)
abstract class CarsDatabase: RoomDatabase(){

    abstract fun carsDao():CarDao

    companion object {
        @Volatile
        private var INSTANCE: CarsDatabase? = null

        fun getDatabase(context: Context): CarsDatabase{
            val tempInstance = INSTANCE
            if(tempInstance != null){
                return tempInstance
            }
            synchronized(this){
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    CarsDatabase::class.java,
                    "cars-database"
                ).fallbackToDestructiveMigration().build()
                INSTANCE = instance
                return instance
            }
        }
    }

}