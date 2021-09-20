package com.example.CarDatabaseApp.room

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import java.io.Serializable


@Entity (tableName = "cars")
data class Car(
 @PrimaryKey(autoGenerate = true)   val id:Int = 0,
 @ColumnInfo
val Brand:String = "",
 @ColumnInfo
val Model:String = "",
 @ColumnInfo
val Price:Int = 0
): Serializable

