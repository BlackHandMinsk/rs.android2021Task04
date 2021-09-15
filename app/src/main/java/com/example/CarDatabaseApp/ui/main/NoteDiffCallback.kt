package com.example.CarDatabaseApp.ui.main

import androidx.recyclerview.widget.DiffUtil
import com.example.CarDatabaseApp.room.Car

class NoteDiffCallback:DiffUtil.ItemCallback<Car>() {
    override fun areItemsTheSame(
        oldItem: Car,
        newItem: Car
    ): Boolean =oldItem.id==newItem.id

    override fun areContentsTheSame(
        oldItem: Car,
        newItem: Car
    ): Boolean = oldItem==newItem
}