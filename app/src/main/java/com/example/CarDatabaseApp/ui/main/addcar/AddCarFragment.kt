package com.example.CarDatabaseApp.ui.main.addcar

import android.annotation.SuppressLint
import android.os.Bundle

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment

import androidx.lifecycle.ViewModelProvider

import androidx.navigation.fragment.findNavController
import com.example.CarDatabaseApp.R
import com.example.CarDatabaseApp.databinding.FragmentAddCarBinding
import com.example.CarDatabaseApp.room.Car

import com.example.CarDatabaseApp.ui.main.utils.inputCheck

class AddCarFragment : Fragment() {
    private lateinit var mViewModel: AddCarViewModel
    private var _binding: FragmentAddCarBinding? = null
    private val binding get() = _binding!!



    @SuppressLint("ShowToast", "SetTextI18n")
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentAddCarBinding.inflate(inflater, container, false)

        mViewModel = ViewModelProvider(this).get(AddCarViewModel::class.java)



        binding.apply {
            addCarToDatabaseBtn.setOnClickListener { insertDataToDatabase() }
        }
        return binding.root
    }

    @SuppressLint("UseSwitchCompatOrMaterialCode")
    private fun insertDataToDatabase() {
        try {
            val brand = binding.addBrandTextview.text.toString()
            val model = binding.addModelTextView.text.toString()
            val price = binding.addPriceTextview.text.toString().toInt()
            if (inputCheck(brand, model, price)) {
                val note = Car(
                    0,
                    brand,
                    model,
                    price
                )

                mViewModel.insert(note)

                findNavController().navigate(R.id.action_addCarFragment_to_mainFragment)
                Log.i("123", "all good")
            } else
                Toast.makeText(requireContext(), "Check correct data", Toast.LENGTH_SHORT).show()
        }catch (e:NumberFormatException){
            Toast.makeText(requireContext(), "Check correct data", Toast.LENGTH_SHORT).show()
        }

    }
}