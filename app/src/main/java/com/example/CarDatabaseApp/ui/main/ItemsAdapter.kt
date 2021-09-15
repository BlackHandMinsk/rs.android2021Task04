package com.example.CarDatabaseApp.ui.main

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.navigation.findNavController
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.ItemTouchUIUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.CarDatabaseApp.databinding.NoteListItemBinding
import com.example.CarDatabaseApp.room.Car

class ItemsAdapter: ListAdapter<Car, ItemsAdapter.MainViewHolder>(NoteDiffCallback()) {

    class MainViewHolder(val binding: NoteListItemBinding): RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MainViewHolder {
        return MainViewHolder(NoteListItemBinding.inflate(LayoutInflater.from(parent.context), parent, false))
    }

    override fun onViewAttachedToWindow(holder: MainViewHolder) {
        holder.itemView.setOnClickListener {
            val action  =
                MainFragmentDirections.actionMainFragmentToUpdateFragment(currentList[holder.bindingAdapterPosition])
            holder.itemView.findNavController().navigate(action)
        }
    }

    override fun onViewDetachedFromWindow(holder: MainViewHolder) {
        super.onViewDetachedFromWindow(holder)
    }

    override fun onBindViewHolder(holder: MainViewHolder, position: Int) {
        holder.binding.brandTextView.text = getItem(position).Brand.toString()
        holder.binding.modelTextView.text = getItem(position).Model.toString()
        holder.binding.priceTextView.text = getItem(position).Price.toString()


    }
}