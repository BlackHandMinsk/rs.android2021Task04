package com.example.CarDatabaseApp.ui.main

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.*

import androidx.fragment.app.Fragment

import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider

import androidx.navigation.fragment.findNavController
import androidx.preference.PreferenceManager

import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.CarDatabaseApp.R
import com.example.CarDatabaseApp.databinding.MainFragmentBinding

import com.example.CarDatabaseApp.ui.main.settings.PreferenceActivity
import com.example.CarDatabaseApp.ui.main.utils.*



class MainFragment : Fragment() {

    private var _binding: MainFragmentBinding? = null
    private val mBinding get() = _binding!!
    lateinit var mViewModel: MainViewModel
    private lateinit var mAdapter: ItemsAdapter


    override fun onAttach(context: Context) {
        val prefs = PreferenceManager.getDefaultSharedPreferences(requireContext())
        SORT = prefs.getString("preference_sort", "Price").toString()
        FILTER = prefs.getString("preference_filter", "id NOT NULL").toString()
        TYPE_DATABASE = when(prefs.getBoolean("database", false)) {
            false -> TYPE_CURSOR
            true -> TYPE_ROOM
        }
        Log.i("123", "DATABASE: $TYPE_DATABASE")
        super.onAttach(context)
    }
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {


        _binding = MainFragmentBinding.inflate(layoutInflater, container, false)
        setHasOptionsMenu(true)

        mAdapter = ItemsAdapter()

        LinearLayoutManager(requireContext(),RecyclerView.VERTICAL,false).apply { mBinding.carsList.layoutManager = this }

        mBinding.carsList.adapter = mAdapter

        val prefs = PreferenceManager.getDefaultSharedPreferences(requireContext())
        val filter = prefs.getString("preference_filter","id NOT NULL")
        val sort = prefs.getString("preference_sort","Price")
        val database = when(prefs.getBoolean("database",false)){
            false->"CURSOR"
            true->"ROOM"
        }
        mBinding.apply {
            filterTextView.text = "Filter: $filter"
            sortTextView.text = "Sort: $sort"
            databaseTextView.text = "DATABASE: $database"
        }
        mViewModel = ViewModelProvider(this).get(MainViewModel::class.java)
        mViewModel.readAllData.observe(viewLifecycleOwner, Observer { list ->
            mAdapter.submitList(list)
            Log.i("123 " , list.toString())
        })
        Log.i("123", "SIZE: ${mViewModel.readAllData.value?.size}")
        mBinding.fab.setOnClickListener {
            findNavController().navigate(R.id.action_mainFragment_to_addCarFragment)
        }

        return mBinding.root
    }


    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.main_menu, menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if(item.itemId == R.id.main_menu){
            val intent = Intent(requireActivity(), PreferenceActivity::class.java)
            startActivity(intent)
            requireActivity().finish()
        }
        return super.onOptionsItemSelected(item)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        mViewModel.readAllData.removeObservers(viewLifecycleOwner)
        mBinding.carsList.adapter = null
        _binding = null
    }
}




