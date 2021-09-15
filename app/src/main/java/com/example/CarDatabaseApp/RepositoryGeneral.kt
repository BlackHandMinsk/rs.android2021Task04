package com.example.CarDatabaseApp

import androidx.lifecycle.LiveData
import com.example.CarDatabaseApp.room.Car

interface RepositoryGeneral {

    val readAllData: LiveData<List<Car>>

    suspend fun  add(note: Car)

    suspend fun update(note: Car)

    suspend fun delete(note: Car)
}