package com.example.CarDatabaseApp.ui.main


import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData

import com.example.CarDatabaseApp.cursor.DatabaseCursor
import com.example.CarDatabaseApp.room.Car
import com.example.CarDatabaseApp.room.CarsDatabase
import com.example.CarDatabaseApp.room.Repository
import com.example.CarDatabaseApp.ui.main.utils.*

class MainViewModel(application: Application) : AndroidViewModel(application) {

   private val mContext = application
   val readAllData: LiveData<List<Car>>
   val db = DatabaseCursor(mContext)

   init {
      when (TYPE_DATABASE) {
         TYPE_ROOM -> {
            val noteDao = CarsDatabase.getDatabase(mContext).carsDao()
            REPOSITORY = Repository(noteDao)
            readAllData = REPOSITORY.readAllData
         }
         else ->
            readAllData = db.getAllNotes(FILTER,SORT)
      }
      Log.i("123", "INIT SORT $SORT AND FILTER $FILTER")
   }
   }