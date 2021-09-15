package com.example.CarDatabaseApp.ui.main.settings

import android.content.Intent
import android.os.Bundle
import com.google.android.material.snackbar.Snackbar
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.navigateUp
import androidx.navigation.ui.setupActionBarWithNavController
import com.example.CarDatabaseApp.MainActivity
import com.example.CarDatabaseApp.R
import com.example.CarDatabaseApp.databinding.ActivityPreferenceBinding


class PreferenceActivity : AppCompatActivity() {

    private lateinit var mToolbar: Toolbar
    private var _binding: ActivityPreferenceBinding? = null
    private val mBinding get() = _binding!!

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _binding = ActivityPreferenceBinding.inflate(layoutInflater)
        setContentView(mBinding.root)
        mToolbar = mBinding.toolbar
        supportFragmentManager.beginTransaction().replace(R.id.pref_frag, PreferencesFragment()).commit()
    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return true
    }

    override fun onBackPressed() {
        val intent = Intent(this, MainActivity::class.java)
        startActivity(intent)
        finish()
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}