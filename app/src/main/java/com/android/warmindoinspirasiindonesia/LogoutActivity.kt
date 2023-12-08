package com.android.warmindoinspirasiindonesia

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

class LogoutActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        clearLoginInfo()

        val intent = Intent(this, MainActivity::class.java)
        startActivity(intent)
        finish()
    }

    private fun clearLoginInfo() {
        val sharedPreferences: SharedPreferences = getSharedPreferences("loginPrefs", Context.MODE_PRIVATE)
        val editor: SharedPreferences.Editor = sharedPreferences.edit()

        val username: String? = sharedPreferences.getString("username", "")

        if (!username.isNullOrEmpty()) {
            val db = DBHelper(this)
            val idPengguna = db.getUserId(username)!!.toInt()
            val currentDate = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()).format(Date())
            val currentTime = SimpleDateFormat("HH:mm:ss", Locale.getDefault()).format(Date())
            val aktivitas = "logout"
            db.addAktivitasPengguna(currentDate, currentTime, idPengguna, aktivitas)
        }

        editor.clear()
        editor.apply()
    }

}
