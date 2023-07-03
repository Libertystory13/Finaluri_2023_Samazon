package com.example.samazon


import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import com.google.android.material.bottomnavigation.BottomNavigationView


class ProfileActivity : AppCompatActivity() {



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_profile)

        val navView = findViewById<BottomNavigationView>(R.id.bottomNavView)

        val controller = findNavController(R.id.nav_host_fragment)

        val appBarConfig = AppBarConfiguration(setOf(
            R.id.storeFragment,
            R.id.cartFragment,
            R.id.profileFragment,
            R.id.settingsFragment

        ))

        setupActionBarWithNavController(controller, appBarConfig)
        navView.setupWithNavController(controller)



        }
    }
