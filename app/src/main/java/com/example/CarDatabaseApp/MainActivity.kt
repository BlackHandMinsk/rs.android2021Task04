package com.example.CarDatabaseApp

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.NavController
import androidx.navigation.findNavController
import androidx.navigation.ui.setupActionBarWithNavController
import com.example.CarDatabaseApp.databinding.MainActivityBinding


class MainActivity : AppCompatActivity() {
    private var _binding: MainActivityBinding? = null
    private val mBinding get() = _binding!!
    lateinit var navController: NavController
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _binding = MainActivityBinding.inflate(layoutInflater)
        setContentView(mBinding.root)
        setupActionBarWithNavController(findNavController(R.id.fragmentContainerView))

    }
    override fun onSupportNavigateUp(): Boolean {
        val navController = findNavController(R.id.fragmentContainerView)
        return navController.navigateUp() || super.onSupportNavigateUp()
    }
    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}