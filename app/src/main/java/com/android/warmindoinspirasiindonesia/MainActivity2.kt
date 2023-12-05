package com.android.warmindoinspirasiindonesia

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.fragment.app.Fragment
import com.google.android.material.bottomnavigation.BottomNavigationView

class MainActivity2 : AppCompatActivity() {

    lateinit var bottomNav: BottomNavigationView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main2)

        if (!isLoggedIn()) {
            val intent = Intent(this, LoginActivity::class.java)
            startActivity(intent)
            finish()
            return
        }

        loadFragment(DashboardFragment())
        bottomNav = findViewById(R.id.bottomNav) as BottomNavigationView
        bottomNav.setOnItemSelectedListener {
            when (it.itemId) {
                R.id.home -> {
                    loadFragment(DashboardFragment())
                    true
                }
                R.id.crud -> {
                    loadFragment(CrudFragment())
                    true
                }
                R.id.settings -> {
                    loadFragment(SettingsFragment())
                    true
                }
                else -> false
            }
        }
    }

    private fun loadFragment(fragment: Fragment) {
        val transaction = supportFragmentManager.beginTransaction()
        transaction.replace(R.id.container, fragment)
        transaction.commit()
    }

    private fun isLoggedIn(): Boolean {
        val sharedPreferences: SharedPreferences = getSharedPreferences("loginPrefs", Context.MODE_PRIVATE)
        return sharedPreferences.getBoolean("isLoggedIn", false)
    }
}
