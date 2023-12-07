package com.android.warmindoinspirasiindonesia

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import at.favre.lib.crypto.bcrypt.BCrypt
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

class LoginActivity : AppCompatActivity(), View.OnClickListener {
    private lateinit var etUsername: EditText
    private lateinit var etPassword: EditText
    private lateinit var btnLogin: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        etUsername = findViewById(R.id.et_username)
        etPassword = findViewById(R.id.et_password)
        btnLogin = findViewById(R.id.btn_login)

        btnLogin.setOnClickListener(this)
    }

    override fun onClick(view: View?) {
        if (view != null) {
            if (view.id == R.id.btn_login) {
                val db = DBHelper(this)

                val username = etUsername.text.toString()
                val password = etPassword.text.toString()

                val storedHashedPassword = db.getHashedPassword(username)

                if (storedHashedPassword != null) {
                    val passwordMatches = BCrypt.verifyer().verify(password.toCharArray(), storedHashedPassword).verified

                    if (passwordMatches) {
                        saveLoginStatus(username)

                        val dashboardIntent = Intent(this, MainActivity2::class.java)
                        startActivity(dashboardIntent)
                        finish()
                    } else {
                        Toast.makeText(this, "Credential doesn't match", Toast.LENGTH_SHORT).show()
                    }
                } else {
                    Toast.makeText(this, "User not found", Toast.LENGTH_SHORT).show()
                }
            }
        }
    }

    private fun saveLoginStatus(username: String) {
        val sharedPreferences: SharedPreferences = getSharedPreferences("loginPrefs", Context.MODE_PRIVATE)
        val editor: SharedPreferences.Editor = sharedPreferences.edit()

        editor.putBoolean("isLoggedIn", true)
        editor.putString("username", username)

        editor.apply()
    }
}