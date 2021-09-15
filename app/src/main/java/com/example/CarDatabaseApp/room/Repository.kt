package com.example.CarDatabaseApp.room

import androidx.lifecycle.LiveData
import com.example.CarDatabaseApp.RepositoryGeneral
import com.example.CarDatabaseApp.ui.main.utils.FILTER
import com.example.CarDatabaseApp.ui.main.utils.SORT
import kotlinx.coroutines.flow.Flow

class Repository(private val noteDao: CarDao):RepositoryGeneral {

    override val readAllData: LiveData<List<Car>>
        get() = noteDao.getAllNotes(FILTER, SORT)


    override  suspend fun add(note: Car) {
        noteDao.insert(note)
    }

    override suspend fun update(note: Car) {
        noteDao.insert(note)

    }

    override suspend fun delete(note: Car) {
        noteDao.delete(note)

    }
}