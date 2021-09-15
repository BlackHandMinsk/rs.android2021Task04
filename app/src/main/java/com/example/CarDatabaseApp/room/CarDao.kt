package com.example.CarDatabaseApp.room

import androidx.lifecycle.LiveData
import androidx.room.*
import kotlinx.coroutines.flow.Flow

@Dao
interface CarDao{
    @Query(
        "SELECT * FROM cars WHERE CASE WHEN :filter ='id' THEN id NOT NULL WHEN :filter ='Price < 10000' THEN Price<10000 WHEN :filter ='Price > 9999' THEN Price>9999 END ORDER BY CASE WHEN :sort ='Brand' THEN Brand WHEN :sort ='Model' THEN Model WHEN :sort ='Price' THEN Price  END ASC")
    fun getAllNotes(
        filter: String,
        sort: String
    ): LiveData<List<Car>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(note: Car)

    @Update
    suspend fun update(note: Car)

    @Delete
    suspend fun delete(note: Car)

}
