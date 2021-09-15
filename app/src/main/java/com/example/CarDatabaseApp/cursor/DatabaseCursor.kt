package com.example.CarDatabaseApp.cursor


import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.liveData
import com.example.CarDatabaseApp.room.Car
import com.example.CarDatabaseApp.room.CarDao
import kotlinx.coroutines.Dispatchers


import kotlinx.coroutines.withContext
import java.sql.SQLException
import java.util.concurrent.Executors

private const val LOG_TAG = "SQLiteOpenHelper"
private const val DATABASE_NAME = "cars-database"
private const val TABLE_NAME = "cars"
private const val DATABASE_VERSION = 2
private const val CREATE_TABLE_SQL =
    "CREATE TABLE IF NOT EXISTS $TABLE_NAME (" +
            "id	INTEGER NOT NULL," +
            "Brand	TEXT NOT NULL," +
            "Model	TEXT NOT NULL," +
            "Price	INTEGER NOT NULL," +
            "PRIMARY KEY(id AUTOINCREMENT)" +
            ");"

private val executor = Executors.newSingleThreadExecutor()

class DatabaseCursor(context: Context) :
    SQLiteOpenHelper(context, DATABASE_NAME, null, DATABASE_VERSION), CarDao {
    override fun onCreate(db: SQLiteDatabase?) {
        try {
            db?.execSQL(CREATE_TABLE_SQL)

        } catch (exception: SQLException) {
            Log.e(LOG_TAG, "SQLiteOpenHelper", exception)
        }
    }

    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {
        Log.d(LOG_TAG, "onUpgrade called")
    }


    private suspend fun getCarsList(sort:String, filter: String):List<Car>{
        return withContext(Dispatchers.IO) {
            val listOfCars = mutableListOf<Car>()
            val db = writableDatabase
            val selectQuery = "SELECT * FROM $TABLE_NAME WHERE $filter ORDER BY $sort"
            val cursor = db.rawQuery(selectQuery,null)
            cursor?.let{
                if (cursor.moveToFirst()) {
                    do {
                        val id = cursor.getInt(cursor.getColumnIndex("id"))
                        val brand = cursor.getString(cursor.getColumnIndex("Brand"))
                        val model = cursor.getString(cursor.getColumnIndex("Model"))
                        val price = cursor.getInt(cursor.getColumnIndex("Price"))
                        listOfCars.add(Car(id, brand, model, price))
                    } while (cursor.moveToNext())
                }
            }
            cursor.close()
            listOfCars
        }
    }
    override fun getAllNotes(filter:String, sort: String): LiveData<List<Car>> {
        return liveData<List<Car>> {
            emit(getCarsList(sort, filter))
        }
    }

    override suspend fun insert(note: Car) {
        Log.d(LOG_TAG, "Cursor addCar($note)")
        val db = writableDatabase
        val values = ContentValues()
        values.put("Brand",note.Brand)
        values.put("Model",note.Model)
        values.put("Price",note.Price)
        db.insert(TABLE_NAME, null, values)
        db.close()
    }

    override suspend fun update(note: Car) {
        Log.d(LOG_TAG, "Cursor updateCar($note)")
        val db = writableDatabase
        val values = ContentValues()
        values.put("id",note.id)
        values.put("Brand",note.Brand)
        values.put("Model",note.Model)
        values.put("Price",note.Price)
        db.update(TABLE_NAME, values, "id" + "=?", arrayOf(note.id.toString()))
        db.close()
    }

    override suspend fun delete(note: Car) {
        Log.d(LOG_TAG, "Cursor deleteCar($note)")
        val db = writableDatabase
        db.delete(TABLE_NAME, "id" + "=?", arrayOf(note.id.toString()))
        db.close()
    }




    fun getCar(id: Int): LiveData<Car?> {

        val carLiveData = MutableLiveData<Car>()
        val db = writableDatabase

        val selectQuery = "SELECT * FROM $TABLE_NAME WHERE id = $id"
        val cursor = db.rawQuery(selectQuery, null)
        Log.d(LOG_TAG, "$cursor")
        if (cursor != null) {
            if (cursor.moveToFirst()) {
                do {

                    val id = cursor.getInt(cursor.getColumnIndex("id"))
                    val brand = cursor.getString(cursor.getColumnIndex("Brand"))
                    val model = cursor.getString(cursor.getColumnIndex("Model"))
                    val price = cursor.getInt(cursor.getColumnIndex("Price"))
                    val car =  Car(id, brand, model, price)
                    Log.d(LOG_TAG, "FROM GET CAR CURSOR $car")
                    carLiveData.value = car
                } while (cursor.moveToNext())
            }
        }
        cursor.close()
        Log.d(LOG_TAG, "FROM GET CAR CURSOR ${carLiveData.value}")
        return carLiveData
    }
}