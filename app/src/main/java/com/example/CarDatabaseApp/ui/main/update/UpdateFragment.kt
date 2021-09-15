package com.example.CarDatabaseApp.ui.main.update

import android.app.AlertDialog
import android.os.Bundle
import android.text.TextUtils
import android.util.Log
import android.view.*
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.example.CarDatabaseApp.R
import com.example.CarDatabaseApp.databinding.FragmentUpdateBinding
import com.example.CarDatabaseApp.room.Car
import com.example.CarDatabaseApp.ui.main.utils.inputCheck
import com.example.emptyviewbinding.update.UpdateViewModel
import kotlinx.android.synthetic.main.fragment_add_car.*
import java.lang.NumberFormatException

class UpdateFragment : Fragment() {

    private val args by navArgs<UpdateFragmentArgs>()

    private lateinit var mViewModel: UpdateViewModel
    private var _binding: FragmentUpdateBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentUpdateBinding.inflate(inflater, container, false)




        mViewModel = ViewModelProvider(this).get(UpdateViewModel::class.java)

        binding.apply {
            updateBrandTextview.setText(args.item.Brand)
            updateModelTextView.setText(args.item.Model)
            updatePriceTextview.setText(args.item.Price.toString())

            updateCarInDatabase.setOnClickListener {
                updateItem()
            }
            setHasOptionsMenu(true)

            return binding.root
        }
    }

    private fun updateItem() {
        try{
        val brand = binding.updateBrandTextview.text.toString()
        val model = binding.updateModelTextView.text.toString()
        val price = binding.updatePriceTextview.text.toString().toInt()

        if (inputCheck(brand, model,price)) {
            val note = Car(
                args.item.id, brand,
                model, price,
            )

            mViewModel.update(note)
            findNavController().navigate(R.id.action_updateFragment_to_mainFragment)
        }
        else
            Toast.makeText(requireContext(), "Check correct data", Toast.LENGTH_SHORT).show()
            }catch (e:NumberFormatException){
            Toast.makeText(requireContext(), "Check correct data", Toast.LENGTH_SHORT).show()
            }
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.menu_delete, menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == R.id.menu_delete) {
            deleteUser()
        }

        return super.onOptionsItemSelected(item)
    }


    private fun deleteUser() {
        val builder = AlertDialog.Builder(requireContext())
        builder.setPositiveButton("Yes") { _, _ ->
            mViewModel.delete(args.item)
            Toast.makeText(
                requireContext(),
                "Successfully removed: ${args.item.Brand}",
                Toast.LENGTH_SHORT
            ).show()
            findNavController().navigate(R.id.action_updateFragment_to_mainFragment)


        }
        builder.setNegativeButton("No") { _, _ -> }
        builder.setTitle("Delete ${args.item.Brand}?")
        builder.setMessage("Are you sure you want to delete ${args.item.Brand}?")
        builder.create().show()
    }
}