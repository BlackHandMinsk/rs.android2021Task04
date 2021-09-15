package com.example.emptyviewbinding.update

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.example.CarDatabaseApp.cursor.DatabaseCursor
import com.example.CarDatabaseApp.room.Car
import com.example.CarDatabaseApp.ui.main.utils.REPOSITORY
import com.example.CarDatabaseApp.ui.main.utils.TYPE_DATABASE
import com.example.CarDatabaseApp.ui.main.utils.TYPE_ROOM

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class UpdateViewModel(application: Application) : AndroidViewModel(application) {
    private val mContext = application

    val db = DatabaseCursor(mContext)

    fun update(note: Car) =
        viewModelScope.launch(Dispatchers.IO) {
            when (TYPE_DATABASE) {
                TYPE_ROOM -> {
                    REPOSITORY.update(note)
                }
                else -> {
                    db.update(note)
                }
            }
        }


    fun delete(note: Car) =
        viewModelScope.launch(Dispatchers.IO) {
            when (TYPE_DATABASE) {
                TYPE_ROOM -> {
                    REPOSITORY.delete(note)
                }
                else -> {
                    db.delete(note)
                }

            }
        }
}
