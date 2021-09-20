package com.example.CarDatabaseApp.ui.main.utils

import android.text.TextUtils
import com.example.CarDatabaseApp.MainActivity
import com.example.CarDatabaseApp.RepositoryGeneral

var SORT = "Price"
var FILTER = "id"

const val TYPE_ROOM = "type_room"
const val TYPE_CURSOR = "type_cursor"

lateinit var REPOSITORY:RepositoryGeneral
var TYPE_DATABASE = ""


 fun inputCheck(brand: String, model: String,price:Int): Boolean {
    return !(TextUtils.isEmpty(brand) || TextUtils.isEmpty(model) || price.toString()==""||brand.length>15||model.length>15|| price !in 0..130000)
}