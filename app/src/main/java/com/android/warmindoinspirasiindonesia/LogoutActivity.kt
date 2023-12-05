package com.android.warmindoinspirasiindonesia

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity

class LogoutActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        clearLoginInfo()

        val intent = Intent(this, LoginActivity::class.java)
        startActivity(intent)
        finish()
    }

    private fun clearLoginInfo() {
        val sharedPreferences: SharedPreferences = getSharedPreferences("loginPrefs", Context.MODE_PRIVATE)
        val editor: SharedPreferences.Editor = sharedPreferences.edit()

        editor.clear()

        editor.apply()
    }
}
